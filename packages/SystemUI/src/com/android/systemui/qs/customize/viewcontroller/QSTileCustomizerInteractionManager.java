package com.android.systemui.qs.customize.viewcontroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        final Looper myLooper = Looper.myLooper();
        myLooper.getClass();
        ?? r0 = new Handler(myLooper) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$mHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = QSTileCustomizerInteractionManager.this;
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
                    final CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
                    final int i2 = messageObjectAnim.touchedPos;
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
                        final CustomizerTileViewPager.CustomizerTilePage customizerTilePage = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                        int indexOf = customizerTilePage.indexOf(customTileInfo);
                        if (indexOf < 0) {
                            return;
                        }
                        int min = Math.min(customizerTilePage.mColumns * customizerTilePage.mMaxRows, customizerTilePage.mCustomTilesInfo.size());
                        int i4 = min - 1;
                        if (i2 > i4) {
                            i2 = i4;
                        }
                        if (CustomizerTileLayout.DEBUG) {
                            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(indexOf, i2, "moveTile from = ", " to  = ", "total = ");
                            m.append(min);
                            m.append("fromtileInfo = ");
                            ExifInterface$$ExternalSyntheticOutline0.m(m, customTileInfo.spec, "CustomizerTileLayout");
                        }
                        AnimatorSet animatorSet = new AnimatorSet();
                        if (indexOf < i2) {
                            int i5 = indexOf;
                            while (i5 < i2) {
                                int i6 = i5 + 1;
                                SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(i6)).customTileView;
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView, "x", ((FrameLayout) customizerTilePage.mBoundaryBox.get(i6)).getLeft(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i5)).getLeft()));
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView, "y", ((FrameLayout) customizerTilePage.mBoundaryBox.get(i6)).getTop(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i5)).getTop()));
                                i5 = i6;
                            }
                        } else {
                            for (int i7 = indexOf; i7 > i2; i7--) {
                                SecCustomizeTileView secCustomizeTileView2 = ((CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(i7 - 1)).customTileView;
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "x", ((FrameLayout) customizerTilePage.mBoundaryBox.get(r12)).getLeft(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i7)).getLeft()));
                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "y", ((FrameLayout) customizerTilePage.mBoundaryBox.get(r12)).getTop(), ((FrameLayout) customizerTilePage.mBoundaryBox.get(i7)).getTop()));
                            }
                        }
                        animatorSet.setDuration(200L);
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.3
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                if (CustomizerTileLayout.DEBUG) {
                                    Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                                }
                                CustomizerTileLayout.this.setCircleTranslation(i2, customTileInfo.isActive);
                            }
                        });
                        animatorSet.start();
                        CustomTileInfo customTileInfo2 = (CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(indexOf);
                        customizerTilePage.mCustomTilesInfo.remove(indexOf);
                        customizerTilePage.mCustomTilesInfo.add(i2, customTileInfo2);
                        return;
                    }
                    if (i3 == 201) {
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).dropTile(customTileInfo, Boolean.FALSE);
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
                                CustomizerTileViewPager customizerTileViewPager2 = CustomizerTileViewPager.this;
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
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).selectTile(customTileInfo, false);
                        return;
                    }
                    if (i3 == 211) {
                        CustomizerTileViewPager.CustomizerTilePage customizerTilePage2 = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                        int indexOf2 = customizerTilePage2.indexOf(customTileInfo);
                        if (indexOf2 < 0) {
                            return;
                        }
                        if (indexOf2 >= customizerTilePage2.mCustomTilesInfo.size()) {
                            indexOf2 = customizerTilePage2.mCustomTilesInfo.size() - 1;
                        }
                        SecCustomizeTileView secCustomizeTileView3 = ((CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(indexOf2)).customTileView;
                        if (CustomizerTileLayout.DEBUG) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(indexOf2, "selectTileByAccessibility position = ", "CustomizerTileLayout");
                        }
                        secCustomizeTileView3.mLabel.setAlpha(0.0f);
                        return;
                    }
                    if (i3 == 210) {
                        int tiledPageIndex = customizerTileViewPager.getTiledPageIndex(customTileInfo);
                        Log.d("CSTMPagedTileLayout", "handleAnimate dropTile: " + customTileInfo + ", pageIndex=" + tiledPageIndex);
                        if (tiledPageIndex == -1) {
                            return;
                        }
                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(tiledPageIndex)).dropTile(customTileInfo, Boolean.TRUE);
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
                if (QSTileCustomizerInteractionManager.this.mView.mIsDragging) {
                    Log.d("QSTileCustomizerController", "onLongClick mView.isDragging() skip : dulicated long click");
                    return true;
                }
                if (view == null) {
                    return false;
                }
                view.setBackgroundResource(0);
                if (view instanceof SecCustomizeTileView) {
                    QSTileCustomizerInteractionManager.this.mLongClickedView = (SecCustomizeTileView) view;
                }
                QSTileCustomizerInteractionManager.this.mLongClickedViewInfo = (CustomTileInfo) view.getTag();
                ViewParent parent = view.getParent();
                View view2 = (View) (parent != null ? parent.getParent() : null);
                QSTileCustomizerInteractionManager.this.mWhereAmI = (view2 != null ? view2.getId() : -1) == R.id.qs_customizer_active_pager ? CustomizerInteractionType.AREA_ACTIVE.getValue() : CustomizerInteractionType.AREA_AVAILABLE.getValue();
                CustomizerTileViewPager customizerTileViewPager = QSTileCustomizerInteractionManager.this.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue() ? QSTileCustomizerInteractionManager.this.mActiveTileLayout : QSTileCustomizerInteractionManager.this.mAvailableTileLayout;
                CustomTileInfo customTileInfo = QSTileCustomizerInteractionManager.this.mLongClickedViewInfo;
                if (customizerTileViewPager.mPages.size() != 0) {
                    ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(customizerTileViewPager.getCurrentItem())).showRemoveIcon(customTileInfo, false);
                }
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = QSTileCustomizerInteractionManager.this;
                CustomTileInfo customTileInfo2 = qSTileCustomizerInteractionManager.mLongClickedViewInfo;
                int i = qSTileCustomizerInteractionManager.mWhereAmI;
                qSTileCustomizerInteractionManager.mLongClickedViewInfo = customTileInfo2;
                qSTileCustomizerInteractionManager.mWhereAmI = i;
                ((AudioManager) qSTileCustomizerInteractionManager.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).playSoundEffect(106);
                SecCustomizeTileView secCustomizeTileView = QSTileCustomizerInteractionManager.this.mLongClickedView;
                if (secCustomizeTileView != null) {
                    secCustomizeTileView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(CustomizerInteractionType.CUSTOMIZER_TILE_DRAG_AND_DROP_NON_DC_MOTOR.getValue()));
                }
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager2 = QSTileCustomizerInteractionManager.this;
                qSTileCustomizerInteractionManager2.tileDragAnimationHelper.animationStart(qSTileCustomizerInteractionManager2.mLongClickedViewInfo, false);
                final TileDragAnimationHelper tileDragAnimationHelper = QSTileCustomizerInteractionManager.this.tileDragAnimationHelper;
                tileDragAnimationHelper.getClass();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(80L);
                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper$animateDrag$1
                    /* JADX WARN: Code restructure failed: missing block: B:12:0x0049, code lost:
                    
                        if (r0.startDragAndDrop(r2, r4, null, 1048832) == true) goto L17;
                     */
                    @Override // android.animation.Animator.AnimatorListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onAnimationEnd(android.animation.Animator r6) {
                        /*
                            r5 = this;
                            com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper r6 = com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper.this
                            com.android.systemui.qs.customize.view.QSTileCustomizerBase r0 = r6.mView
                            com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager r6 = r6.interactionManager
                            com.android.systemui.qs.customize.SecCustomizeTileView r1 = r6.mLongClickedView
                            int r6 = r6.mWhereAmI
                            r6 = 0
                            if (r1 == 0) goto L10
                            float r2 = r1.mPointX
                            goto L11
                        L10:
                            r2 = r6
                        L11:
                            if (r1 == 0) goto L16
                            float r3 = r1.mPointY
                            goto L17
                        L16:
                            r3 = r6
                        L17:
                            r0.getClass()
                            com.android.systemui.qs.customize.view.QSTileCustomizerBase$1 r4 = new com.android.systemui.qs.customize.view.QSTileCustomizerBase$1
                            r4.<init>(r0, r1, r2, r3)
                            android.content.ClipDescription r0 = new android.content.ClipDescription
                            r1 = 0
                            java.lang.String[] r1 = new java.lang.String[r1]
                            java.lang.String r2 = ""
                            r0.<init>(r2, r1)
                            android.content.ClipData$Item r1 = new android.content.ClipData$Item
                            android.content.Intent r2 = new android.content.Intent
                            r2.<init>()
                            r1.<init>(r2)
                            android.content.ClipData r2 = new android.content.ClipData
                            r2.<init>(r0, r1)
                            com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper r0 = com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper.this     // Catch: java.lang.IllegalStateException -> L4c
                            com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager r0 = r0.interactionManager     // Catch: java.lang.IllegalStateException -> L4c
                            com.android.systemui.qs.customize.SecCustomizeTileView r0 = r0.mLongClickedView     // Catch: java.lang.IllegalStateException -> L4c
                            if (r0 == 0) goto L4c
                            r1 = 0
                            r3 = 1048832(0x100100, float:1.469727E-39)
                            boolean r0 = r0.startDragAndDrop(r2, r4, r1, r3)     // Catch: java.lang.IllegalStateException -> L4c
                            r1 = 1
                            if (r0 != r1) goto L4c
                            goto L53
                        L4c:
                            com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper r0 = com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper.this
                            com.android.systemui.qs.customize.view.QSTileCustomizerBase r0 = r0.mView
                            r0.requestLayout()
                        L53:
                            com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper r0 = com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper.this
                            com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager r0 = r0.interactionManager
                            com.android.systemui.qs.customize.SecCustomizeTileView r0 = r0.mLongClickedView
                            if (r0 == 0) goto L5e
                            r0.setAlpha(r6)
                        L5e:
                            com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper r5 = com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper.this
                            com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager r6 = r5.interactionManager
                            com.android.systemui.qs.customize.SecCustomizeTileView r6 = r6.mLongClickedView
                            if (r6 == 0) goto L70
                            com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper$animateDrag$1$onAnimationEnd$2 r0 = new com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper$animateDrag$1$onAnimationEnd$2
                            r0.<init>()
                            r1 = 100
                            r6.postDelayed(r0, r1)
                        L70:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.viewcontroller.TileDragAnimationHelper$animateDrag$1.onAnimationEnd(android.animation.Animator):void");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        SecCustomizeTileView secCustomizeTileView2 = TileDragAnimationHelper.this.interactionManager.mLongClickedView;
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
                ofFloat.start();
                int i2 = StringCompanionObject.$r8$clinit;
                String string = QSTileCustomizerInteractionManager.this.context.getString(R.string.qs_custom_action_long_pressed);
                CustomTileInfo customTileInfo3 = QSTileCustomizerInteractionManager.this.mLongClickedViewInfo;
                customTileInfo3.getClass();
                view.announceForAccessibility(String.format(string, Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo3.customTileView.getTag()).state.label}, 1)));
                return true;
            }
        };
        this.dragListener = new View.OnDragListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$2
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                if (!QSTileCustomizerInteractionManager.this.mView.isShown()) {
                    return false;
                }
                Integer valueOf = dragEvent != null ? Integer.valueOf(dragEvent.getAction()) : null;
                int intValue = ((Integer) (view != null ? view.getTag() : null)).intValue();
                View view2 = (View) view.getParent().getParent();
                if (valueOf != null && valueOf.intValue() == 3) {
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = QSTileCustomizerInteractionManager.this;
                    qSTileCustomizerInteractionManager.mIsDroppedOnView = true;
                    qSTileCustomizerInteractionManager.tileDragAnimationHelper.animationDrop(qSTileCustomizerInteractionManager.mLongClickedViewInfo);
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager2 = QSTileCustomizerInteractionManager.this;
                    QSTileCustomizerBase qSTileCustomizerBase2 = qSTileCustomizerInteractionManager2.mView;
                    qSTileCustomizerBase2.mIsMultiTouch = false;
                    qSTileCustomizerBase2.mIsDragging = false;
                    qSTileCustomizerBase2.mAvailableTileLayout.mIsMultiTouch = false;
                    qSTileCustomizerBase2.mActiveTileLayout.mIsMultiTouch = false;
                    int i = StringCompanionObject.$r8$clinit;
                    String string = qSTileCustomizerInteractionManager2.context.getString(R.string.qs_custom_action_move_done);
                    CustomTileInfo customTileInfo = QSTileCustomizerInteractionManager.this.mLongClickedViewInfo;
                    customTileInfo.getClass();
                    view.announceForAccessibility(String.format(string, Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label}, 1)));
                    return true;
                }
                if (valueOf != null && valueOf.intValue() == 5) {
                    if (view2.getId() == R.id.qs_customizer_available_pager && QSTileCustomizerInteractionManager.this.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue()) {
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager3 = QSTileCustomizerInteractionManager.this;
                        qSTileCustomizerInteractionManager3.tileDragAnimationHelper.animateArea(qSTileCustomizerInteractionManager3.mLongClickedViewInfo, CustomizerInteractionType.ACTIVE_TO_AVAILABLE.getValue(), intValue);
                    } else if (view2.getId() == R.id.qs_customizer_active_pager && QSTileCustomizerInteractionManager.this.mWhereAmI == CustomizerInteractionType.AREA_AVAILABLE.getValue()) {
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager4 = QSTileCustomizerInteractionManager.this;
                        qSTileCustomizerInteractionManager4.tileDragAnimationHelper.animateArea(qSTileCustomizerInteractionManager4.mLongClickedViewInfo, CustomizerInteractionType.AVAILABLE_TO_ACTIVE.getValue(), intValue);
                    } else {
                        QSTileCustomizerInteractionManager.this.getClass();
                        if (view.getId() != R.id.scroll_top_area && view.getId() != R.id.qs_customize_top_summary_buttons) {
                            QSTileCustomizerInteractionManager.this.getClass();
                            if (view.getId() != R.id.scroll_bottom_area && view.getId() != R.id.qs_edit_available_text) {
                                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager5 = QSTileCustomizerInteractionManager.this;
                                qSTileCustomizerInteractionManager5.tileDragAnimationHelper.animateCurrentPage(qSTileCustomizerInteractionManager5.mLongClickedViewInfo, intValue);
                            }
                        }
                    }
                    QSTileCustomizerBase qSTileCustomizerBase3 = QSTileCustomizerInteractionManager.this.mView;
                    boolean z = view2.getId() == R.id.qs_customizer_available_pager;
                    CustomizerTileViewPager customizerTileViewPager = z ? qSTileCustomizerBase3.mAvailableTileLayout : qSTileCustomizerBase3.mActiveTileLayout;
                    int columnMaxCountInPage = customizerTileViewPager.getColumnMaxCountInPage();
                    int columnCount = customizerTileViewPager.getColumnCount();
                    int i2 = intValue % columnMaxCountInPage;
                    int i3 = columnCount == 0 ? -1 : i2 % columnCount;
                    view.announceForAccessibility(String.format(qSTileCustomizerBase3.mContext.getString(z ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((columnCount != 0 ? i2 / columnCount : -1) + 1), Integer.valueOf(i3 + 1)));
                    return true;
                }
                if (valueOf != null && valueOf.intValue() == 1) {
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager6 = QSTileCustomizerInteractionManager.this;
                    qSTileCustomizerInteractionManager6.mIsDroppedOnView = false;
                    qSTileCustomizerInteractionManager6.mView.mIsDragging = true;
                    return true;
                }
                if (valueOf != null && valueOf.intValue() == 2) {
                    QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager7 = QSTileCustomizerInteractionManager.this;
                    if (!qSTileCustomizerInteractionManager7.mIsTopEdit && qSTileCustomizerInteractionManager7.mWhereAmI == CustomizerInteractionType.AREA_ACTIVE.getValue()) {
                        QSTileCustomizerInteractionManager.this.getClass();
                        if (view.getId() == R.id.scroll_top_area || view.getId() == R.id.qs_customize_top_summary_buttons) {
                            QSTileCustomizerInteractionManager.this.mActiveScrollView.smoothScrollBy(0, -10);
                            return true;
                        }
                        QSTileCustomizerInteractionManager.this.getClass();
                        if (view.getId() != R.id.scroll_bottom_area && view.getId() != R.id.qs_edit_available_text) {
                            return true;
                        }
                        QSTileCustomizerInteractionManager.this.mActiveScrollView.smoothScrollBy(0, 10);
                        return true;
                    }
                } else {
                    if (valueOf != null && valueOf.intValue() == 6) {
                        QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.removeAreaAnimationMessage();
                        return true;
                    }
                    if (valueOf != null && valueOf.intValue() == 4) {
                        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager8 = QSTileCustomizerInteractionManager.this;
                        if (!qSTileCustomizerInteractionManager8.mIsDroppedOnView) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(qSTileCustomizerInteractionManager8.mWhereAmI, "ACTION_DRAG_ENDED mWhereAmI = ", "QSTileCustomizerController");
                            QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager9 = QSTileCustomizerInteractionManager.this;
                            qSTileCustomizerInteractionManager9.tileDragAnimationHelper.animationDrop(qSTileCustomizerInteractionManager9.mLongClickedViewInfo);
                            QSTileCustomizerInteractionManager.this.mIsDroppedOnView = true;
                        }
                        QSTileCustomizerBase qSTileCustomizerBase4 = QSTileCustomizerInteractionManager.this.mView;
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
                QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = QSTileCustomizerInteractionManager.this;
                if (!qSTileCustomizerInteractionManager.mIsReadyToClick || qSTileCustomizerInteractionManager.mView.mIsDragging) {
                    return;
                }
                qSTileCustomizerInteractionManager.mIsReadyToClick = false;
                View view2 = view.getId() == SecQSSettingEditResources.REMOVE_ICON_ID ? (View) view.getTag() : view;
                int id = ((View) view2.getParent().getParent()).getId();
                final CustomTileInfo customTileInfo = (CustomTileInfo) view2.getTag();
                if (id == R.id.qs_customizer_active_pager) {
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animateArea(customTileInfo, CustomizerInteractionType.ACTIVE_TO_AVAILABLE.getValue(), 19998);
                    final QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager2 = QSTileCustomizerInteractionManager.this;
                    qSTileCustomizerInteractionManager2.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationDropOtherPage(customTileInfo);
                        }
                    }, 100L);
                    int i = StringCompanionObject.$r8$clinit;
                    view.announceForAccessibility(String.format(QSTileCustomizerInteractionManager.this.context.getString(R.string.qs_custom_action_removed_done), Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label}, 1)));
                } else if (id == R.id.qs_customizer_available_pager) {
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animateArea(customTileInfo, CustomizerInteractionType.AVAILABLE_TO_ACTIVE.getValue(), 9999);
                    final QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager3 = QSTileCustomizerInteractionManager.this;
                    qSTileCustomizerInteractionManager3.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationDrop(customTileInfo);
                        }
                    }, 100L);
                    int i2 = StringCompanionObject.$r8$clinit;
                    view.announceForAccessibility(String.format(QSTileCustomizerInteractionManager.this.context.getString(R.string.qs_custom_action_added_done), Arrays.copyOf(new Object[]{((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label}, 1)));
                }
                final QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager4 = QSTileCustomizerInteractionManager.this;
                qSTileCustomizerInteractionManager4.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSTileCustomizerInteractionManager.this.mIsReadyToClick = true;
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
            return new CustomActionMoveItem(this.context, customTileInfo, customizerTileViewPager2, customizerTileViewPager, this.mWhereAmI == CustomizerInteractionType.AREA_AVAILABLE.getValue(), new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                    int intValue = ((Number) obj2).intValue();
                    QSTileCustomizerInteractionManager.this.getClass();
                    Log.d("QSTileCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + intValue);
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationStart(customTileInfo2, true);
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animateCurrentPage(customTileInfo2, intValue);
                }
            }, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationDropOtherPage((CustomTileInfo) obj);
                }
            }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$7
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                    CustomizerTileViewPager.this.moveTile(customTileInfo2, ((Number) obj2).intValue());
                    this.tileDragAnimationHelper.animationDrop(customTileInfo2);
                    this.sendAnnouncementEvent(R.string.qs_custom_action_move_done);
                }
            }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                    int intValue = ((Number) obj2).intValue();
                    TileDragAnimationHelper tileDragAnimationHelper = QSTileCustomizerInteractionManager.this.tileDragAnimationHelper;
                    int value = CustomizerInteractionType.ACTIVE_TO_AVAILABLE.getValue();
                    int columnMaxCountInPage = intValue % customizerTileViewPager.getColumnMaxCountInPage();
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
        return new CustomActionMoveItem(this.context, customTileInfo, customizerTileViewPager, customizerTileViewPager2, this.mWhereAmI == customizerInteractionType.getValue(), new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                int intValue = ((Number) obj2).intValue();
                QSTileCustomizerInteractionManager.this.getClass();
                Log.d("QSTileCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + intValue);
                QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationStart(customTileInfo2, true);
                QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animateCurrentPage(customTileInfo2, intValue % customizerTileViewPager.getColumnMaxCountInPage());
            }
        }, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSTileCustomizerInteractionManager.this.tileDragAnimationHelper.animationDropOtherPage((CustomTileInfo) obj);
            }
        }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                CustomizerTileViewPager.this.moveTile(customTileInfo2, ((Number) obj2).intValue());
                this.tileDragAnimationHelper.animationDrop(customTileInfo2);
                this.sendAnnouncementEvent(R.string.qs_custom_action_move_done);
            }
        }, new BiConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$createCustomActionMoveItem$4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) obj;
                int intValue = ((Number) obj2).intValue();
                TileDragAnimationHelper tileDragAnimationHelper = QSTileCustomizerInteractionManager.this.tileDragAnimationHelper;
                int value = CustomizerInteractionType.AVAILABLE_TO_ACTIVE.getValue();
                int columnMaxCountInPage = intValue % customizerTileViewPager2.getColumnMaxCountInPage();
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
        AccessibilityEvent obtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        obtain.getText().clear();
        obtain.getText().add(this.context.getResources().getString(i));
        obtain.setPackageName(this.context.getPackageName());
        accessibilityManager.sendAccessibilityEvent(obtain);
    }
}
