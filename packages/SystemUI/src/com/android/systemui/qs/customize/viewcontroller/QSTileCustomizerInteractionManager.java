package com.android.systemui.qs.customize.viewcontroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomActionMoveItem;
import com.android.systemui.qs.customize.CustomTileInfo;
import com.android.systemui.qs.customize.CustomizerTileLayout;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.MessageObjectAnim;
import com.android.systemui.qs.customize.SecCustomizeTileView;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.QSTileCustomizerBase;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: classes2.dex */
public final class QSTileCustomizerInteractionManager {
    public final QSTileCustomizerInteractionManager$initializeListeners$3 clickListener;
    public final Context context;
    public final QSTileCustomizerInteractionManager$initializeListeners$2 dragListener;
    public final QSTileCustomizerInteractionManager$initializeListeners$1 longClickListener;
    public final ScrollView mActiveScrollView;
    public final CustomizerTileViewPager mActiveTileLayout;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public final QSTileCustomizerInteractionManager$mHandler$1 mHandler;
    public boolean mIsDroppedOnView;
    public boolean mIsReadyToClick = true;
    public final boolean mIsTopEdit;
    public SecCustomizeTileView mLongClickedView;
    public CustomTileInfo mLongClickedViewInfo;
    public final SecQSCustomizerTileAdapter mTileAdapter;
    public final int mTopMinMaxNum;
    public final QSTileCustomizerBase mView;
    public int mWhereAmI;
    public final TileDragAnimationHelper tileDragAnimationHelper;

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.Handler, com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$mHandler$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$2] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3] */
    public QSTileCustomizerInteractionManager(QSTileCustomizerBase qSTileCustomizerBase, SecQSCustomizerTileAdapter secQSCustomizerTileAdapter) {
        this.mView = qSTileCustomizerBase;
        this.mTileAdapter = secQSCustomizerTileAdapter;
        this.context = qSTileCustomizerBase.getContext();
        this.mIsTopEdit = qSTileCustomizerBase.mIsTopEdit;
        this.mActiveScrollView = (ScrollView) qSTileCustomizerBase.findViewById(R.id.qs_active_page_scrollview);
        this.mActiveTileLayout = (CustomizerTileViewPager) qSTileCustomizerBase.findViewById(R.id.qs_customizer_active_pager);
        this.mAvailableTileLayout = (CustomizerTileViewPager) qSTileCustomizerBase.findViewById(R.id.qs_customizer_available_pager);
        final Looper looperMyLooper = Looper.myLooper();
        looperMyLooper.getClass();
        ?? r0 = new Handler(looperMyLooper) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$mHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) throws Resources.NotFoundException {
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = this.this$0;
                qSTileCustomizerInteractionManager.getClass();
                ListPopupWindow$$ExternalSyntheticOutline0.m(message.what, "handleMessage() msg.what=", "QSTileCustomizerController");
                final CustomizerTileViewPager customizerTileViewPager = qSTileCustomizerInteractionManager.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue() ? qSTileCustomizerInteractionManager.mActiveTileLayout : qSTileCustomizerInteractionManager.mAvailableTileLayout;
                MessageObjectAnim messageObjectAnim = (MessageObjectAnim) message.obj;
                if (messageObjectAnim != null) {
                    int i = message.what;
                    if (i != CustomizerInteractionType.MSG_HANDLE_ANIMATE_DROP.getValue() && i != CustomizerInteractionType.MSG_HANDLE_ANIMATE_START.getValue() && i != CustomizerInteractionType.MSG_HANDLE_ANIMATE_PAGE.getValue()) {
                        if (i == CustomizerInteractionType.MSG_HANDLE_ANIMATE_AREA.getValue()) {
                            qSTileCustomizerInteractionManager.tileDragAnimationHelper.moveToArea(messageObjectAnim, qSTileCustomizerInteractionManager.mTileAdapter);
                            return;
                        }
                        return;
                    }
                    if (customizerTileViewPager.mPages.size() == 0) {
                        return;
                    }
                    final int currentItem = customizerTileViewPager.getCurrentItem();
                    CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
                    int i2 = messageObjectAnim.touchedPos;
                    if (customTileInfo == null) {
                        Log.e("CSTMPagedTileLayout", "TileInfo is null");
                        return;
                    }
                    StringBuilder sb = new StringBuilder("handleAnimate addInfo.spce");
                    sb.append(customTileInfo.spec);
                    sb.append("animation type = ");
                    RecyclerView$$ExternalSyntheticOutline0.m(messageObjectAnim.animationType, "CSTMPagedTileLayout", sb);
                    int i3 = messageObjectAnim.animationType;
                    if (i3 == 202) {
                        CustomizerTileViewPager.CustomizerTilePage customizerTilePage = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                        int iIndexOf = customizerTilePage.indexOf(customTileInfo);
                        if (iIndexOf < 0) {
                            return;
                        }
                        int iMin = Math.min(customizerTilePage.mColumns * customizerTilePage.mMaxRows, customizerTilePage.mCustomTilesInfo.size());
                        int i4 = iMin - 1;
                        if (i2 > i4) {
                            i2 = i4;
                        }
                        if (CustomizerTileLayout.DEBUG) {
                            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iIndexOf, i2, "moveTile from = ", " to  = ", "total = ");
                            sbM.append(iMin);
                            sbM.append("fromtileInfo = ");
                            ExifInterface$$ExternalSyntheticOutline0.m(sbM, customTileInfo.spec, "CustomizerTileLayout");
                        }
                        AnimatorSet animatorSet = new AnimatorSet();
                        if (iIndexOf < i2) {
                            int i5 = iIndexOf;
                            while (i5 < i2) {
                                int i6 = i5 + 1;
                                SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(i6)).customTileView;
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView, "x", ((FrameLayout) customizerTilePage.mBoundaryBox.get(i6)).getLeft(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i5)).getLeft()));
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView, "y", ((FrameLayout) customizerTilePage.mBoundaryBox.get(i6)).getTop(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i5)).getTop()));
                                i5 = i6;
                            }
                        } else {
                            for (int i7 = iIndexOf; i7 > i2; i7--) {
                                SecCustomizeTileView secCustomizeTileView2 = ((CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(i7 - 1)).customTileView;
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "x", ((FrameLayout) customizerTilePage.mBoundaryBox.get(r12)).getLeft(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i7)).getLeft()));
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "y", ((FrameLayout) customizerTilePage.mBoundaryBox.get(r12)).getTop(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i7)).getTop()));
                            }
                        }
                        animatorSet.setDuration(200L);
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.3
                            public final /* synthetic */ int val$emptyPos;
                            public final /* synthetic */ CustomTileInfo val$fromtileInfo;

                            public AnonymousClass3(int i22, CustomTileInfo customTileInfo2) {
                                i = i22;
                                customTileInfo = customTileInfo2;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) throws Resources.NotFoundException {
                                if (CustomizerTileLayout.DEBUG) {
                                    Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                                }
                                CustomizerTileLayout.this.setCircleTranslation(i, customTileInfo.isActive);
                            }
                        });
                        animatorSet.start();
                        CustomTileInfo customTileInfo2 = (CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(iIndexOf);
                        customizerTilePage.mCustomTilesInfo.remove(iIndexOf);
                        customizerTilePage.mCustomTilesInfo.add(i22, customTileInfo2);
                        return;
                    }
                    if (i3 == 201) {
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).dropTile(customTileInfo2, Boolean.FALSE);
                        return;
                    }
                    if (i3 == 203 || i3 == 204) {
                        if (customizerTileViewPager.mPages.size() == 0) {
                            return;
                        }
                        int i8 = messageObjectAnim.animationType;
                        if (i8 == 204) {
                            CustomizerTileViewPager.AnonymousClass1 anonymousClass1 = customizerTileViewPager.mAdapter;
                            if (currentItem >= (anonymousClass1 != null ? anonymousClass1.getCount() : 0) - 1) {
                                return;
                            }
                        } else if (currentItem <= 0) {
                            return;
                        }
                        final int i9 = i8 == 204 ? 1 : -1;
                        int columnCount = i8 == 204 ? 0 : (customizerTileViewPager.getColumnCount() * customizerTileViewPager.mRows) - 1;
                        final int columnCount2 = i8 == 204 ? (customizerTileViewPager.getColumnCount() * customizerTileViewPager.mRows) - 1 : 0;
                        int columnCount3 = (customizerTileViewPager.getColumnCount() * customizerTileViewPager.mRows) - 1;
                        final int i10 = currentItem + i9;
                        final CustomTileInfo info = ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i10)).getInfo(columnCount);
                        if (info == null) {
                            return;
                        }
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i10)).removeTile(info, false);
                        final CustomTileInfo customTileInfo3 = messageObjectAnim.longClickedTileInfo;
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i10)).addTile(customTileInfo3, columnCount3, false);
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i10)).selectTile(customTileInfo3, true);
                        customizerTileViewPager.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CustomizerTileViewPager customizerTileViewPager2 = customizerTileViewPager;
                                int i11 = currentItem;
                                int i12 = i9;
                                int i13 = i10;
                                CustomTileInfo customTileInfo4 = customTileInfo3;
                                CustomTileInfo customTileInfo5 = info;
                                int i14 = columnCount2;
                                int i15 = CustomizerTileViewPager.$r8$clinit;
                                customizerTileViewPager2.getClass();
                                Log.d("CSTMPagedTileLayout", "cur " + i11 + "pageOffset" + i12);
                                if (i11 >= customizerTileViewPager2.mPages.size() || customizerTileViewPager2.mPages.get(i11) == null) {
                                    return;
                                }
                                customizerTileViewPager2.setCurrentItem(i13, true);
                                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(i11)).removeTile(customTileInfo4, false);
                                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(i11)).addTile(customTileInfo5, i14, false);
                                FrameLayout frameLayout = ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(i11)).mCircle;
                                if (frameLayout != null) {
                                    frameLayout.setAlpha(0.0f);
                                }
                            }
                        }, 210L);
                        return;
                    }
                    if (i3 == 200) {
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).selectTile(customTileInfo2, false);
                        return;
                    }
                    if (i3 == 211) {
                        CustomizerTileViewPager.CustomizerTilePage customizerTilePage2 = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                        int iIndexOf2 = customizerTilePage2.indexOf(customTileInfo2);
                        if (iIndexOf2 < 0) {
                            return;
                        }
                        if (iIndexOf2 >= customizerTilePage2.mCustomTilesInfo.size()) {
                            iIndexOf2 = customizerTilePage2.mCustomTilesInfo.size() - 1;
                        }
                        SecCustomizeTileView secCustomizeTileView3 = ((CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(iIndexOf2)).customTileView;
                        if (CustomizerTileLayout.DEBUG) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(iIndexOf2, "selectTileByAccessibility position = ", "CustomizerTileLayout");
                        }
                        secCustomizeTileView3.mLabel.setAlpha(0.0f);
                        return;
                    }
                    if (i3 == 210) {
                        int tiledPageIndex = customizerTileViewPager.getTiledPageIndex(customTileInfo2);
                        Log.d("CSTMPagedTileLayout", "handleAnimate dropTile: " + customTileInfo2 + ", pageIndex=" + tiledPageIndex);
                        if (tiledPageIndex == -1) {
                            return;
                        }
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(tiledPageIndex)).dropTile(customTileInfo2, Boolean.TRUE);
                    }
                }
            }
        };
        this.mHandler = r0;
        this.tileDragAnimationHelper = new TileDragAnimationHelper(qSTileCustomizerBase, r0, this);
        this.mTopMinMaxNum = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQsTileMinNum(qSTileCustomizerBase.getContext());
        this.longClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                if (this.this$0.mView.mIsDragging) {
                    Log.d("QSTileCustomizerController", "onLongClick mView.isDragging() skip : dulicated long click");
                    return true;
                }
                if (view == null) {
                    return false;
                }
                view.setBackgroundResource(0);
                if (view instanceof SecCustomizeTileView) {
                    this.this$0.mLongClickedView = (SecCustomizeTileView) view;
                }
                this.this$0.mLongClickedViewInfo = (CustomTileInfo) view.getTag();
                ViewParent parent = view.getParent();
                View view2 = (View) (parent != null ? parent.getParent() : null);
                this.this$0.mWhereAmI = (view2 != null ? view2.getId() : -1) == R.id.qs_customizer_active_pager ? CustomizerInteractionType.AREA_ACTIVE.getValue() : CustomizerInteractionType.AREA_AVAILABLE.getValue();
                CustomizerTileViewPager customizerTileViewPager = this.this$0.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue() ? this.this$0.mActiveTileLayout : this.this$0.mAvailableTileLayout;
                CustomTileInfo customTileInfo = this.this$0.mLongClickedViewInfo;
                if (customizerTileViewPager.mPages.size() != 0) {
                    ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(customizerTileViewPager.getCurrentItem())).showRemoveIcon(customTileInfo, false);
                }
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = this.this$0;
                CustomTileInfo customTileInfo2 = qSTileCustomizerInteractionManager.mLongClickedViewInfo;
                int i = qSTileCustomizerInteractionManager.mWhereAmI;
                qSTileCustomizerInteractionManager.mLongClickedViewInfo = customTileInfo2;
                qSTileCustomizerInteractionManager.mWhereAmI = i;
                ((AudioManager) qSTileCustomizerInteractionManager.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).playSoundEffect(106);
                SecCustomizeTileView secCustomizeTileView = this.this$0.mLongClickedView;
                if (secCustomizeTileView != null) {
                    secCustomizeTileView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(CustomizerInteractionType.CUSTOMIZER_TILE_DRAG_AND_DROP_NON_DC_MOTOR.getValue()));
                }
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager2 = this.this$0;
                qSTileCustomizerInteractionManager2.tileDragAnimationHelper.animationStart(qSTileCustomizerInteractionManager2.mLongClickedViewInfo, false);
                final TileDragAnimationHelper tileDragAnimationHelper = this.this$0.tileDragAnimationHelper;
                tileDragAnimationHelper.getClass();
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration(80L);
                valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper$animateDrag$1
                    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
                    @Override // android.animation.Animator.AnimatorListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onAnimationEnd(Animator animator) {
                        SecCustomizeTileView secCustomizeTileView2;
                        TileDragAnimationHelper tileDragAnimationHelper2 = tileDragAnimationHelper;
                        QSTileCustomizerBase qSTileCustomizerBase2 = tileDragAnimationHelper2.mView;
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager3 = tileDragAnimationHelper2.interactionManager;
                        SecCustomizeTileView secCustomizeTileView3 = qSTileCustomizerInteractionManager3.mLongClickedView;
                        int i2 = qSTileCustomizerInteractionManager3.mWhereAmI;
                        float f = secCustomizeTileView3 != null ? secCustomizeTileView3.mPointX : 0.0f;
                        float f2 = secCustomizeTileView3 != null ? secCustomizeTileView3.mPointY : 0.0f;
                        qSTileCustomizerBase2.getClass();
                        QSTileCustomizerBase.AnonymousClass1 anonymousClass1 = new View.DragShadowBuilder(qSTileCustomizerBase2, secCustomizeTileView3, f, f2) { // from class: com.android.systemui.qs.customize.view.QSTileCustomizerBase.1
                            public final /* synthetic */ float val$pointX;
                            public final /* synthetic */ float val$pointY;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(QSTileCustomizerBase qSTileCustomizerBase22, View secCustomizeTileView32, float f3, float f22) {
                                super(secCustomizeTileView32);
                                this.val$pointX = f3;
                                this.val$pointY = f22;
                            }

                            @Override // android.view.View.DragShadowBuilder
                            public final void onProvideShadowMetrics(Point point, Point point2) {
                                point.set(getView().getWidth(), getView().getHeight());
                                point2.set((int) this.val$pointX, (int) this.val$pointY);
                            }
                        };
                        ClipData clipData = new ClipData(new ClipDescription("", new String[0]), new ClipData.Item(new Intent()));
                        try {
                            secCustomizeTileView2 = tileDragAnimationHelper.interactionManager.mLongClickedView;
                        } catch (IllegalStateException unused) {
                        }
                        if (secCustomizeTileView2 != null) {
                            if (!secCustomizeTileView2.startDragAndDrop(clipData, anonymousClass1, null, 1048832)) {
                                tileDragAnimationHelper.mView.requestLayout();
                            }
                        }
                        SecCustomizeTileView secCustomizeTileView4 = tileDragAnimationHelper.interactionManager.mLongClickedView;
                        if (secCustomizeTileView4 != null) {
                            secCustomizeTileView4.setAlpha(0.0f);
                        }
                        final TileDragAnimationHelper tileDragAnimationHelper3 = tileDragAnimationHelper;
                        SecCustomizeTileView secCustomizeTileView5 = tileDragAnimationHelper3.interactionManager.mLongClickedView;
                        if (secCustomizeTileView5 != null) {
                            secCustomizeTileView5.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper$animateDrag$1$onAnimationEnd$2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TileDragAnimationHelper tileDragAnimationHelper4 = tileDragAnimationHelper3;
                                    if (tileDragAnimationHelper4.mView.mIsDragging) {
                                        return;
                                    }
                                    tileDragAnimationHelper4.animationDrop(tileDragAnimationHelper4.interactionManager.mLongClickedViewInfo);
                                }
                            }, 100L);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        SecCustomizeTileView secCustomizeTileView2 = tileDragAnimationHelper.interactionManager.mLongClickedView;
                        if (secCustomizeTileView2 != null) {
                            secCustomizeTileView2.mLabelContainer.setVisibility(8);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }
                });
                valueAnimatorOfFloat.start();
                int i2 = StringCompanionObject.$r8$clinit;
                String string = this.this$0.context.getString(R.string.qs_custom_action_long_pressed);
                CustomTileInfo customTileInfo3 = this.this$0.mLongClickedViewInfo;
                customTileInfo3.getClass();
                view.announceForAccessibility(String.format(string, Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo3.customTileView.getTag()).state.label}, 1)));
                return true;
            }
        };
        this.dragListener = new View.OnDragListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$2
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                if (!this.this$0.mView.isShown()) {
                    return false;
                }
                Integer numValueOf = dragEvent != null ? Integer.valueOf(dragEvent.getAction()) : null;
                int iIntValue = ((Integer) (view != null ? view.getTag() : null)).intValue();
                View view2 = (View) view.getParent().getParent();
                if (numValueOf != null && numValueOf.intValue() == 3) {
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = this.this$0;
                    qSTileCustomizerInteractionManager.mIsDroppedOnView = true;
                    qSTileCustomizerInteractionManager.tileDragAnimationHelper.animationDrop(qSTileCustomizerInteractionManager.mLongClickedViewInfo);
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager2 = this.this$0;
                    QSTileCustomizerBase qSTileCustomizerBase2 = qSTileCustomizerInteractionManager2.mView;
                    qSTileCustomizerBase2.mIsMultiTouch = false;
                    qSTileCustomizerBase2.mIsDragging = false;
                    qSTileCustomizerBase2.mAvailableTileLayout.mIsMultiTouch = false;
                    qSTileCustomizerBase2.mActiveTileLayout.mIsMultiTouch = false;
                    int i = StringCompanionObject.$r8$clinit;
                    String string = qSTileCustomizerInteractionManager2.context.getString(R.string.qs_custom_action_move_done);
                    CustomTileInfo customTileInfo = this.this$0.mLongClickedViewInfo;
                    customTileInfo.getClass();
                    view.announceForAccessibility(String.format(string, Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label}, 1)));
                    return true;
                }
                if (numValueOf != null && numValueOf.intValue() == 5) {
                    if (view2.getId() == R.id.qs_customizer_available_pager && this.this$0.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue()) {
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager3 = this.this$0;
                        qSTileCustomizerInteractionManager3.tileDragAnimationHelper.animateArea(qSTileCustomizerInteractionManager3.mLongClickedViewInfo, CustomizerInteractionType.ACTIVE_TO_AVAILABLE.getValue(), iIntValue);
                    } else if (view2.getId() == R.id.qs_customizer_active_pager && this.this$0.mWhereAmI == CustomizerInteractionType.AREA_AVAILABLE.getValue()) {
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager4 = this.this$0;
                        qSTileCustomizerInteractionManager4.tileDragAnimationHelper.animateArea(qSTileCustomizerInteractionManager4.mLongClickedViewInfo, CustomizerInteractionType.AVAILABLE_TO_ACTIVE.getValue(), iIntValue);
                    } else {
                        this.this$0.getClass();
                        if (view.getId() != R.id.scroll_top_area && view.getId() != R.id.qs_customize_top_summary_buttons) {
                            this.this$0.getClass();
                            if (view.getId() != R.id.scroll_bottom_area && view.getId() != R.id.qs_edit_available_text) {
                                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager5 = this.this$0;
                                qSTileCustomizerInteractionManager5.tileDragAnimationHelper.animateCurrentPage(qSTileCustomizerInteractionManager5.mLongClickedViewInfo, iIntValue);
                            }
                        }
                    }
                    QSTileCustomizerBase qSTileCustomizerBase3 = this.this$0.mView;
                    boolean z = view2.getId() == R.id.qs_customizer_available_pager;
                    CustomizerTileViewPager customizerTileViewPager = z ? qSTileCustomizerBase3.mAvailableTileLayout : qSTileCustomizerBase3.mActiveTileLayout;
                    int columnMaxCountInPage = customizerTileViewPager.getColumnMaxCountInPage();
                    int columnCount = customizerTileViewPager.getColumnCount();
                    int i2 = iIntValue % columnMaxCountInPage;
                    int i3 = columnCount == 0 ? -1 : i2 % columnCount;
                    view.announceForAccessibility(String.format(qSTileCustomizerBase3.mContext.getString(z ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((columnCount != 0 ? i2 / columnCount : -1) + 1), Integer.valueOf(i3 + 1)));
                    return true;
                }
                if (numValueOf != null && numValueOf.intValue() == 1) {
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager6 = this.this$0;
                    qSTileCustomizerInteractionManager6.mIsDroppedOnView = false;
                    qSTileCustomizerInteractionManager6.mView.mIsDragging = true;
                    return true;
                }
                if (numValueOf != null && numValueOf.intValue() == 2) {
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager7 = this.this$0;
                    if (!qSTileCustomizerInteractionManager7.mIsTopEdit && qSTileCustomizerInteractionManager7.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue()) {
                        this.this$0.getClass();
                        if (view.getId() == R.id.scroll_top_area || view.getId() == R.id.qs_customize_top_summary_buttons) {
                            this.this$0.mActiveScrollView.smoothScrollBy(0, -10);
                            return true;
                        }
                        this.this$0.getClass();
                        if (view.getId() != R.id.scroll_bottom_area && view.getId() != R.id.qs_edit_available_text) {
                            return true;
                        }
                        this.this$0.mActiveScrollView.smoothScrollBy(0, 10);
                        return true;
                    }
                } else {
                    if (numValueOf != null && numValueOf.intValue() == 6) {
                        this.this$0.tileDragAnimationHelper.removeAreaAnimationMessage();
                        return true;
                    }
                    if (numValueOf != null && numValueOf.intValue() == 4) {
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager8 = this.this$0;
                        if (!qSTileCustomizerInteractionManager8.mIsDroppedOnView) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(qSTileCustomizerInteractionManager8.mWhereAmI, "ACTION_DRAG_ENDED mWhereAmI = ", "QSTileCustomizerController");
                            QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager9 = this.this$0;
                            qSTileCustomizerInteractionManager9.tileDragAnimationHelper.animationDrop(qSTileCustomizerInteractionManager9.mLongClickedViewInfo);
                            this.this$0.mIsDroppedOnView = true;
                        }
                        QSTileCustomizerBase qSTileCustomizerBase4 = this.this$0.mView;
                        qSTileCustomizerBase4.mIsMultiTouch = false;
                        qSTileCustomizerBase4.mIsDragging = false;
                        qSTileCustomizerBase4.mAvailableTileLayout.mIsMultiTouch = false;
                        qSTileCustomizerBase4.mActiveTileLayout.mIsMultiTouch = false;
                    }
                }
                return true;
            }
        };
        this.clickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = this.this$0;
                if (!qSTileCustomizerInteractionManager.mIsReadyToClick || qSTileCustomizerInteractionManager.mView.mIsDragging) {
                    return;
                }
                qSTileCustomizerInteractionManager.mIsReadyToClick = false;
                View view2 = view.getId() == SecQSSettingEditResources.REMOVE_ICON_ID ? (View) view.getTag() : view;
                int id = ((View) view2.getParent().getParent()).getId();
                final CustomTileInfo customTileInfo = (CustomTileInfo) view2.getTag();
                if (id == R.id.qs_customizer_active_pager) {
                    this.this$0.tileDragAnimationHelper.animateArea(customTileInfo, CustomizerInteractionType.ACTIVE_TO_AVAILABLE.getValue(), 19998);
                    final QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager2 = this.this$0;
                    qSTileCustomizerInteractionManager2.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            qSTileCustomizerInteractionManager2.tileDragAnimationHelper.animationDropOtherPage(customTileInfo);
                        }
                    }, 100L);
                    int i = StringCompanionObject.$r8$clinit;
                    view.announceForAccessibility(String.format(this.this$0.context.getString(R.string.qs_custom_action_removed_done), Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label}, 1)));
                } else if (id == R.id.qs_customizer_available_pager) {
                    this.this$0.tileDragAnimationHelper.animateArea(customTileInfo, CustomizerInteractionType.AVAILABLE_TO_ACTIVE.getValue(), 9999);
                    final QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager3 = this.this$0;
                    qSTileCustomizerInteractionManager3.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            qSTileCustomizerInteractionManager3.tileDragAnimationHelper.animationDrop(customTileInfo);
                        }
                    }, 100L);
                    int i2 = StringCompanionObject.$r8$clinit;
                    view.announceForAccessibility(String.format(this.this$0.context.getString(R.string.qs_custom_action_added_done), Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label}, 1)));
                }
                final QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager4 = this.this$0;
                qSTileCustomizerInteractionManager4.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        qSTileCustomizerInteractionManager4.mIsReadyToClick = true;
                    }
                }, 500L);
            }
        };
    }

    public final CustomActionMoveItem createCustomActionMoveItem(CustomTileInfo customTileInfo, final CustomizerTileViewPager customizerTileViewPager, final CustomizerTileViewPager customizerTileViewPager2) {
        ArrayList tilesInfo = customizerTileViewPager.getTilesInfo();
        ArrayList tilesInfo2 = customizerTileViewPager2.getTilesInfo();
        if (!tilesInfo.contains(customTileInfo) && !tilesInfo2.contains(customTileInfo)) {
            return null;
        }
        if (!tilesInfo.contains(customTileInfo)) {
            this.mWhereAmI = CustomizerInteractionType.AREA_ACTIVE.getValue();
            return new CustomActionMoveItem(this.context, customTileInfo, customizerTileViewPager2, customizerTileViewPager, this.mWhereAmI == CustomizerInteractionType.AREA_AVAILABLE.getValue(), new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                    int iIntValue = ((Number) obj2).intValue();
                    QSTileCustomizerInteractionManager.this.getClass();
                    Log.d("QSTileCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + iIntValue);
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationStart(customTileInfo2, true);
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animateCurrentPage(customTileInfo2, iIntValue);
                }
            }, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationDropOtherPage((CustomTileInfo) obj);
                }
            }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.7
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                    customizerTileViewPager2.moveTile(customTileInfo2, ((Number) obj2).intValue());
                    this.tileDragAnimationHelper.animationDrop(customTileInfo2);
                    this.sendAnnouncementEvent(R.string.qs_custom_action_move_done);
                }
            }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                    int iIntValue = ((Number) obj2).intValue();
                    TileDragAnimationHelper tileDragAnimationHelper = QSTileCustomizerInteractionManager.this.tileDragAnimationHelper;
                    int value = CustomizerInteractionType.ACTIVE_TO_AVAILABLE.getValue();
                    int columnMaxCountInPage = iIntValue % customizerTileViewPager.getColumnMaxCountInPage();
                    SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = QSTileCustomizerInteractionManager.this.mTileAdapter;
                    tileDragAnimationHelper.getClass();
                    MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
                    messageObjectAnim.animationType = value;
                    messageObjectAnim.touchedPos = columnMaxCountInPage;
                    messageObjectAnim.longClickedTileInfo = customTileInfo2;
                    tileDragAnimationHelper.moveToArea(messageObjectAnim, secQSCustomizerTileAdapter);
                    tileDragAnimationHelper.animationDrop(customTileInfo2);
                    QSTileCustomizerInteractionManager.this.sendAnnouncementEvent(R.string.qs_custom_action_move_done);
                }
            }, true);
        }
        CustomizerInteractionType customizerInteractionType = CustomizerInteractionType.AREA_AVAILABLE;
        this.mWhereAmI = customizerInteractionType.getValue();
        final boolean z = !this.mIsTopEdit || customizerTileViewPager2.getMinimumTileNum() < this.mTopMinMaxNum;
        return new CustomActionMoveItem(this.context, customTileInfo, customizerTileViewPager, customizerTileViewPager2, this.mWhereAmI == customizerInteractionType.getValue(), new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                int iIntValue = ((Number) obj2).intValue();
                QSTileCustomizerInteractionManager.this.getClass();
                Log.d("QSTileCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + iIntValue);
                QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationStart(customTileInfo2, true);
                QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animateCurrentPage(customTileInfo2, iIntValue % customizerTileViewPager.getColumnMaxCountInPage());
            }
        }, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationDropOtherPage((CustomTileInfo) obj);
            }
        }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                customizerTileViewPager.moveTile(customTileInfo2, ((Number) obj2).intValue());
                this.tileDragAnimationHelper.animationDrop(customTileInfo2);
                this.sendAnnouncementEvent(R.string.qs_custom_action_move_done);
            }
        }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager.createCustomActionMoveItem.4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                int iIntValue = ((Number) obj2).intValue();
                TileDragAnimationHelper tileDragAnimationHelper = QSTileCustomizerInteractionManager.this.tileDragAnimationHelper;
                int value = CustomizerInteractionType.AVAILABLE_TO_ACTIVE.getValue();
                int columnMaxCountInPage = iIntValue % customizerTileViewPager2.getColumnMaxCountInPage();
                SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = QSTileCustomizerInteractionManager.this.mTileAdapter;
                tileDragAnimationHelper.getClass();
                MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
                messageObjectAnim.animationType = value;
                messageObjectAnim.touchedPos = columnMaxCountInPage;
                messageObjectAnim.longClickedTileInfo = customTileInfo2;
                tileDragAnimationHelper.moveToArea(messageObjectAnim, secQSCustomizerTileAdapter);
                tileDragAnimationHelper.animationDrop(customTileInfo2);
                if (z) {
                    QSTileCustomizerInteractionManager.this.sendAnnouncementEvent(R.string.qs_custom_action_move_done);
                }
            }
        }, z);
    }

    public final void sendAnnouncementEvent(int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.context.getSystemService("accessibility");
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        accessibilityEventObtain.getText().clear();
        accessibilityEventObtain.getText().add(this.context.getResources().getString(i));
        accessibilityEventObtain.setPackageName(this.context.getPackageName());
        accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
    }
}
