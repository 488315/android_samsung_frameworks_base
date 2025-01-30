package com.android.systemui.qs.customize;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.SecQSCustomizerBase;
import com.android.systemui.qs.customize.SecQSCustomizerController;
import com.android.systemui.qs.customize.setting.SecQSSettingEditResources;
import com.android.systemui.qs.customize.setting.SecQSSettingEditTilesActivity$onCreate$5;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import kotlin.collections.builders.ListBuilder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSCustomizerController extends ViewController {
    public final int ACTIVE_LEFT_PAGE_AREA;
    public final int ACTIVE_RIGHT_PAGE_AREA;
    public final CustomizerTileViewPager mActiveTileLayout;
    public final SecQSCustomizerAnimator mAnimator;
    public final AudioManager mAudioManager;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public final SecQSCustomizerController$$ExternalSyntheticLambda0 mClickListener;
    public final Context mContext;
    public int mCurrentOrientation;
    public CustomActionManager mCustomActionManager;
    public CustomActionMoveItem mCustomActionMoveItem;
    public View mDoneButton;
    public SecQSSettingEditTilesActivity$onCreate$5 mDoneCallBack;
    public final ViewOnClickListenerC21391 mDoneOnClickListener;
    public final ViewOnDragListenerC21488 mDragListener;
    public final SecQSSettingEditResources mEditResources;
    public final HandlerC21499 mHandler;
    public boolean mIsDroppedOnView;
    public boolean mIsReadyToClick;
    public boolean mIsReadyToMove;
    public final boolean mIsTopEdit;
    public final ViewOnLongClickListenerC21466 mLongClickListener;
    public SecCustomizeTileView mLongClickedView;
    public SecQSCustomizerBase.CustomTileInfo mLongClickedViewInfo;
    public float mPointX;
    public float mPointY;
    public View mResetButton;
    public SystemUIDialog mResetDialog;
    public final ViewOnClickListenerC21422 mResetOnClickListener;
    public final SecQSCustomizerTileAdapter mTileAdapter;
    public final int mTopMinMaxNum;
    public int mWhereAmI;

    /* renamed from: -$$Nest$manimatePage, reason: not valid java name */
    public static void m1624$$Nest$manimatePage(SecQSCustomizerController secQSCustomizerController, SecQSCustomizerBase.CustomTileInfo customTileInfo, int i) {
        if (secQSCustomizerController.mIsReadyToMove) {
            SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
            messageObjectAnim.animationType = i;
            messageObjectAnim.longClickedTileInfo = customTileInfo;
            HandlerC21499 handlerC21499 = secQSCustomizerController.mHandler;
            if (handlerC21499.hasMessages(102)) {
                handlerC21499.removeMessages(102);
            }
            handlerC21499.sendMessage(handlerC21499.obtainMessage(102, messageObjectAnim));
            secQSCustomizerController.mIsReadyToMove = false;
            handlerC21499.postDelayed(new SecQSCustomizerController$$ExternalSyntheticLambda2(secQSCustomizerController, 2), 200L);
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.customize.SecQSCustomizerController$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.customize.SecQSCustomizerController$2] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.customize.SecQSCustomizerController$6] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qs.customize.SecQSCustomizerController$8] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.qs.customize.SecQSCustomizerController$9] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda0] */
    public SecQSCustomizerController(SecQSCustomizerBase secQSCustomizerBase, SecQSSettingEditResources secQSSettingEditResources, boolean z) {
        super(secQSCustomizerBase);
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter;
        this.mIsReadyToMove = true;
        this.mIsReadyToClick = true;
        this.mWhereAmI = 0;
        this.ACTIVE_LEFT_PAGE_AREA = 1000;
        this.ACTIVE_RIGHT_PAGE_AREA = 2000;
        this.mIsDroppedOnView = false;
        this.mCurrentOrientation = -1;
        this.mDoneOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                if (((SecQSCustomizerBase) secQSCustomizerController.mView).isShown()) {
                    List spec = secQSCustomizerController.mActiveTileLayout.getSpec();
                    List quickPanelItems = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).getQuickPanelItems();
                    int size = quickPanelItems != null ? quickPanelItems.size() : 0;
                    boolean z2 = secQSCustomizerController.mIsTopEdit;
                    int i = secQSCustomizerController.mTopMinMaxNum;
                    if (((z2 && ((ArrayList) spec).size() != i && size == 0) ? (char) 65535 : (char) 0) == 65535) {
                        Toast.makeText(((SecQSCustomizerBase) secQSCustomizerController.mView).getContext(), ((SecQSCustomizerBase) secQSCustomizerController.mView).getContext().getResources().getQuantityString(R.plurals.sec_qs_add_minimum, i, Integer.valueOf(i)), 0).show();
                    } else {
                        secQSCustomizerController.close();
                    }
                }
            }
        };
        this.mResetOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog = SecQSCustomizerController.this.mResetDialog;
                if (systemUIDialog == null || !systemUIDialog.isShowing()) {
                    final SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                    if (secQSCustomizerController.mResetDialog == null) {
                        SystemUIDialog systemUIDialog2 = new SystemUIDialog(((SecQSCustomizerBase) secQSCustomizerController.mView).getContext(), 2132018528);
                        secQSCustomizerController.mResetDialog = systemUIDialog2;
                        systemUIDialog2.setMessage(R.string.sec_qs_edit_panel_reset_dialog_message);
                        secQSCustomizerController.mResetDialog.setPositiveButton(R.string.quick_settings_reset, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.4
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("SecQSCustomizerController", "RESET");
                                SecQSCustomizerController secQSCustomizerController2 = SecQSCustomizerController.this;
                                StringBuilder sb = new StringBuilder("reset =  ");
                                SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSCustomizerController2.mTileAdapter;
                                sb.append(secQSCustomizerTileAdapter2.mCurrentSpecs);
                                Log.d("SecQSCustomizerTileAdapter", sb.toString());
                                ArrayList arrayList = new ArrayList();
                                ArrayList arrayList2 = new ArrayList();
                                ArrayList arrayList3 = new ArrayList();
                                CustomizerTileViewPager customizerTileViewPager = secQSCustomizerController2.mActiveTileLayout;
                                List spec = customizerTileViewPager.getSpec();
                                boolean z2 = secQSCustomizerTileAdapter2.mIsTopEdit;
                                QSTileHost qSTileHost = secQSCustomizerTileAdapter2.mHost;
                                String qQSDefaultTileList = z2 ? secQSCustomizerTileAdapter2.mQQSHost.getQQSDefaultTileList() : qSTileHost.getDefaultTileList();
                                Log.d("SecQSCustomizerTileAdapter", "cscTileList: " + qQSDefaultTileList);
                                arrayList.addAll(customizerTileViewPager.getTilesInfo());
                                CustomizerTileViewPager customizerTileViewPager2 = secQSCustomizerController2.mAvailableTileLayout;
                                arrayList.addAll(customizerTileViewPager2.getTilesInfo());
                                for (String str : qQSDefaultTileList.split(",")) {
                                    String changeOldOSTileNameToNewName = qSTileHost.changeOldOSTileNameToNewName(str);
                                    if (!qSTileHost.isSystemTile(changeOldOSTileNameToNewName)) {
                                        if (changeOldOSTileNameToNewName.startsWith("custom(")) {
                                            changeOldOSTileNameToNewName = qSTileHost.getCustomTileNameFromSpec(changeOldOSTileNameToNewName);
                                        }
                                        if (qSTileHost.isAvailableCustomTile(changeOldOSTileNameToNewName)) {
                                            changeOldOSTileNameToNewName = qSTileHost.getCustomTileSpecFromTileName(changeOldOSTileNameToNewName);
                                        }
                                    }
                                    Iterator it = arrayList.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            SecQSCustomizerBase.CustomTileInfo customTileInfo = (SecQSCustomizerBase.CustomTileInfo) it.next();
                                            if (customTileInfo.spec.equals(changeOldOSTileNameToNewName)) {
                                                customTileInfo.isActive = true;
                                                arrayList2.add(customTileInfo);
                                                arrayList.remove(customTileInfo);
                                                arrayList3.add(changeOldOSTileNameToNewName);
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!arrayList3.equals(spec)) {
                                    customizerTileViewPager.addTiles(arrayList2);
                                    Iterator it2 = arrayList.iterator();
                                    while (it2.hasNext()) {
                                        ((SecQSCustomizerBase.CustomTileInfo) it2.next()).isActive = false;
                                    }
                                    customizerTileViewPager2.addTiles(arrayList);
                                    Settings.Secure.putStringForUser(qSTileHost.mContext.getContentResolver(), "sysui_removed_qs_tiles", "", ((UserTrackerImpl) qSTileHost.mUserTracker).getUserId());
                                }
                                if (!((SecQSCustomizerBase) SecQSCustomizerController.this.mView).isShown()) {
                                    SecQSCustomizerController.this.save();
                                }
                                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1021");
                            }
                        });
                        secQSCustomizerController.mResetDialog.setNegativeButton(R.string.f788no, null);
                        secQSCustomizerController.mResetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.5
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                SecQSCustomizerController.this.mResetDialog = null;
                            }
                        });
                        secQSCustomizerController.mResetDialog.show();
                    }
                }
            }
        };
        new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                if (secQSCustomizerController.mCurrentOrientation != configuration.orientation) {
                    if (!QpRune.QUICK_TABLET) {
                        secQSCustomizerController.close();
                        return;
                    }
                    secQSCustomizerController.setupPager();
                    ((SecQSCustomizerBase) secQSCustomizerController.mView).updateResources();
                    secQSCustomizerController.setUpPageArea();
                    secQSCustomizerController.mCurrentOrientation = configuration.orientation;
                }
            }
        };
        this.mLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.6
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(final View view) {
                if (((SecQSCustomizerBase) SecQSCustomizerController.this.mView).mIsDragging) {
                    Log.d("SecQSCustomizerController", "onLongClick mView.isDragging() skip : dulicated long click");
                    return true;
                }
                view.setBackgroundResource(0);
                SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                secQSCustomizerController.mLongClickedView = (SecCustomizeTileView) view;
                secQSCustomizerController.mLongClickedViewInfo = (SecQSCustomizerBase.CustomTileInfo) view.getTag();
                SecQSCustomizerController secQSCustomizerController2 = SecQSCustomizerController.this;
                SecCustomizeTileView secCustomizeTileView = secQSCustomizerController2.mLongClickedView;
                secQSCustomizerController2.mPointX = secCustomizeTileView.mPointX;
                secQSCustomizerController2.mPointY = secCustomizeTileView.mPointY;
                SecQSCustomizerController.this.mWhereAmI = ((View) view.getParent().getParent()).getId() == R.id.qs_customizer_active_pager ? 5000 : PluginEdgeLightingPlus.VERSION;
                SecQSCustomizerController secQSCustomizerController3 = SecQSCustomizerController.this;
                CustomizerTileViewPager customizerTileViewPager = secQSCustomizerController3.mWhereAmI == 5000 ? secQSCustomizerController3.mActiveTileLayout : secQSCustomizerController3.mAvailableTileLayout;
                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(customizerTileViewPager.getCurrentItem())).showRemoveIcon(secQSCustomizerController3.mLongClickedViewInfo, false);
                SecQSCustomizerController secQSCustomizerController4 = SecQSCustomizerController.this;
                SecQSCustomizerBase.CustomTileInfo customTileInfo = secQSCustomizerController4.mLongClickedViewInfo;
                int i = secQSCustomizerController4.mWhereAmI;
                secQSCustomizerController4.mLongClickedViewInfo = customTileInfo;
                secQSCustomizerController4.mWhereAmI = i;
                (i == 5000 ? secQSCustomizerController4.mActiveTileLayout : secQSCustomizerController4.mAvailableTileLayout).mLongClickedViewInfo = customTileInfo;
                AudioManager audioManager = secQSCustomizerController4.mAudioManager;
                if (audioManager != null) {
                    audioManager.playSoundEffect(106);
                }
                SecQSCustomizerController.this.mLongClickedView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                SecQSCustomizerController secQSCustomizerController5 = SecQSCustomizerController.this;
                secQSCustomizerController5.animationStart(secQSCustomizerController5.mLongClickedViewInfo, Boolean.FALSE);
                SecQSCustomizerController secQSCustomizerController6 = SecQSCustomizerController.this;
                secQSCustomizerController6.getClass();
                final float f = -Math.abs(view.getWidth() * 0.125f);
                final float f2 = -Math.abs(view.getHeight() * 0.25f);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(80L);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda7
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        View view2 = view;
                        float f3 = f;
                        float f4 = f2;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        view2.setTranslationX(f3 * animatedFraction);
                        view2.setTranslationY(f4 * animatedFraction);
                    }
                });
                ofFloat.addListener(secQSCustomizerController6.new C21477(view));
                ofFloat.start();
                return true;
            }
        };
        this.mDragListener = new View.OnDragListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.8
            /* JADX WARN: Removed duplicated region for block: B:24:0x00b1  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x00b4  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x00cc  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x00d0  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x00b7  */
            @Override // android.view.View.OnDragListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onDrag(View view, DragEvent dragEvent) {
                if (!((SecQSCustomizerBase) SecQSCustomizerController.this.mView).isShown()) {
                    return false;
                }
                int action = dragEvent.getAction();
                int intValue = ((Integer) view.getTag()).intValue();
                View view2 = (View) view.getParent().getParent();
                if (action == 1) {
                    SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                    secQSCustomizerController.mIsDroppedOnView = false;
                    ((SecQSCustomizerBase) secQSCustomizerController.mView).mIsDragging = true;
                } else if (action == 3) {
                    SecQSCustomizerController secQSCustomizerController2 = SecQSCustomizerController.this;
                    secQSCustomizerController2.mIsDroppedOnView = true;
                    secQSCustomizerController2.animationDrop(secQSCustomizerController2.mLongClickedViewInfo);
                    SecQSCustomizerController secQSCustomizerController3 = SecQSCustomizerController.this;
                    SecQSCustomizerBase secQSCustomizerBase2 = (SecQSCustomizerBase) secQSCustomizerController3.mView;
                    secQSCustomizerBase2.mIsMultiTouch = false;
                    secQSCustomizerBase2.mIsDragging = false;
                    secQSCustomizerBase2.mAvailableTileLayout.mIsMultiTouch = false;
                    secQSCustomizerBase2.mActiveTileLayout.mIsMultiTouch = false;
                    view.announceForAccessibility(secQSCustomizerController3.getContext().getString(R.string.qs_custom_action_move_done));
                } else if (action == 4) {
                    if (!SecQSCustomizerController.this.mIsDroppedOnView) {
                        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("ACTION_DRAG_ENDED mWhereAmI = "), SecQSCustomizerController.this.mWhereAmI, "SecQSCustomizerController");
                        SecQSCustomizerController secQSCustomizerController4 = SecQSCustomizerController.this;
                        secQSCustomizerController4.animationDrop(secQSCustomizerController4.mLongClickedViewInfo);
                        SecQSCustomizerController.this.mIsDroppedOnView = true;
                    }
                    SecQSCustomizerBase secQSCustomizerBase3 = (SecQSCustomizerBase) SecQSCustomizerController.this.mView;
                    secQSCustomizerBase3.mIsMultiTouch = false;
                    secQSCustomizerBase3.mIsDragging = false;
                    secQSCustomizerBase3.mAvailableTileLayout.mIsMultiTouch = false;
                    secQSCustomizerBase3.mActiveTileLayout.mIsMultiTouch = false;
                } else if (action == 5) {
                    if (view2.getId() == R.id.qs_customizer_available_pager) {
                        SecQSCustomizerController secQSCustomizerController5 = SecQSCustomizerController.this;
                        if (secQSCustomizerController5.mWhereAmI == 5000) {
                            secQSCustomizerController5.animateArea(secQSCustomizerController5.mLongClickedViewInfo, 1000, intValue);
                            SecQSCustomizerBase secQSCustomizerBase4 = (SecQSCustomizerBase) SecQSCustomizerController.this.mView;
                            boolean z2 = view2.getId() == R.id.qs_customizer_available_pager;
                            CustomizerTileViewPager customizerTileViewPager = z2 ? secQSCustomizerBase4.mAvailableTileLayout : secQSCustomizerBase4.mActiveTileLayout;
                            int i = intValue % (customizerTileViewPager.mColumns * customizerTileViewPager.mRows);
                            int columnCount = i % customizerTileViewPager.getColumnCount();
                            view.announceForAccessibility(String.format(secQSCustomizerBase4.mContext.getString(z2 ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((i / customizerTileViewPager.getColumnCount()) + 1), Integer.valueOf(columnCount + 1)));
                        }
                    }
                    if (view2.getId() == R.id.qs_customizer_active_pager) {
                        SecQSCustomizerController secQSCustomizerController6 = SecQSCustomizerController.this;
                        if (secQSCustomizerController6.mWhereAmI == 6000) {
                            secQSCustomizerController6.animateArea(secQSCustomizerController6.mLongClickedViewInfo, 2000, intValue);
                            SecQSCustomizerBase secQSCustomizerBase42 = (SecQSCustomizerBase) SecQSCustomizerController.this.mView;
                            if (view2.getId() == R.id.qs_customizer_available_pager) {
                            }
                            if (z2) {
                            }
                            int i2 = intValue % (customizerTileViewPager.mColumns * customizerTileViewPager.mRows);
                            int columnCount2 = i2 % customizerTileViewPager.getColumnCount();
                            view.announceForAccessibility(String.format(secQSCustomizerBase42.mContext.getString(z2 ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((i2 / customizerTileViewPager.getColumnCount()) + 1), Integer.valueOf(columnCount2 + 1)));
                        }
                    }
                    if (view.getId() == R.id.active_page_area_right) {
                        SecQSCustomizerController secQSCustomizerController7 = SecQSCustomizerController.this;
                        if (secQSCustomizerController7.mWhereAmI != 6000) {
                            SecQSCustomizerController.m1624$$Nest$manimatePage(secQSCustomizerController7, secQSCustomizerController7.mLongClickedViewInfo, 204);
                            SecQSCustomizerBase secQSCustomizerBase422 = (SecQSCustomizerBase) SecQSCustomizerController.this.mView;
                            if (view2.getId() == R.id.qs_customizer_available_pager) {
                            }
                            if (z2) {
                            }
                            int i22 = intValue % (customizerTileViewPager.mColumns * customizerTileViewPager.mRows);
                            int columnCount22 = i22 % customizerTileViewPager.getColumnCount();
                            view.announceForAccessibility(String.format(secQSCustomizerBase422.mContext.getString(z2 ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((i22 / customizerTileViewPager.getColumnCount()) + 1), Integer.valueOf(columnCount22 + 1)));
                        }
                    }
                    if (view.getId() == R.id.active_page_area_left) {
                        SecQSCustomizerController secQSCustomizerController8 = SecQSCustomizerController.this;
                        if (secQSCustomizerController8.mWhereAmI != 6000) {
                            SecQSCustomizerController.m1624$$Nest$manimatePage(secQSCustomizerController8, secQSCustomizerController8.mLongClickedViewInfo, 203);
                            SecQSCustomizerBase secQSCustomizerBase4222 = (SecQSCustomizerBase) SecQSCustomizerController.this.mView;
                            if (view2.getId() == R.id.qs_customizer_available_pager) {
                            }
                            if (z2) {
                            }
                            int i222 = intValue % (customizerTileViewPager.mColumns * customizerTileViewPager.mRows);
                            int columnCount222 = i222 % customizerTileViewPager.getColumnCount();
                            view.announceForAccessibility(String.format(secQSCustomizerBase4222.mContext.getString(z2 ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((i222 / customizerTileViewPager.getColumnCount()) + 1), Integer.valueOf(columnCount222 + 1)));
                        }
                    }
                    SecQSCustomizerController secQSCustomizerController9 = SecQSCustomizerController.this;
                    secQSCustomizerController9.animateCurrentPage(secQSCustomizerController9.mLongClickedViewInfo, intValue);
                    SecQSCustomizerBase secQSCustomizerBase42222 = (SecQSCustomizerBase) SecQSCustomizerController.this.mView;
                    if (view2.getId() == R.id.qs_customizer_available_pager) {
                    }
                    if (z2) {
                    }
                    int i2222 = intValue % (customizerTileViewPager.mColumns * customizerTileViewPager.mRows);
                    int columnCount2222 = i2222 % customizerTileViewPager.getColumnCount();
                    view.announceForAccessibility(String.format(secQSCustomizerBase42222.mContext.getString(z2 ? R.string.qs_custom_action_move_from_available_to_available : R.string.qs_custom_action_move_from_available_to_active), Integer.valueOf((i2222 / customizerTileViewPager.getColumnCount()) + 1), Integer.valueOf(columnCount2222 + 1)));
                } else if (action == 6) {
                    SecQSCustomizerController.this.removeAreaAnimationMessage();
                }
                return true;
            }
        };
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.9
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("handleMessage() msg.what="), message.what, "SecQSCustomizerController");
                SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                CustomizerTileViewPager customizerTileViewPager = secQSCustomizerController.mWhereAmI == 5000 ? secQSCustomizerController.mActiveTileLayout : secQSCustomizerController.mAvailableTileLayout;
                switch (message.what) {
                    case 100:
                    case 101:
                    case 102:
                        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = (SecQSCustomizerBase.MessageObjectAnim) message.obj;
                        int currentItem = customizerTileViewPager.getCurrentItem();
                        final SecQSCustomizerBase.CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
                        final int i = messageObjectAnim.touchedPos;
                        if (customTileInfo != null) {
                            StringBuilder sb = new StringBuilder("handleAnimate addInfo.spce");
                            sb.append(customTileInfo.spec);
                            sb.append("animation type = ");
                            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, messageObjectAnim.animationType, "CSTMPagedTileLayout");
                            int i2 = messageObjectAnim.animationType;
                            if (i2 != 202) {
                                if (i2 != 201) {
                                    if (i2 != 203 && i2 != 204) {
                                        if (i2 != 200) {
                                            if (i2 != 211) {
                                                if (i2 == 210) {
                                                    int tiledPageIndex = customizerTileViewPager.getTiledPageIndex(customTileInfo);
                                                    Log.d("CSTMPagedTileLayout", "handleAnimate dropTile: " + customTileInfo + ", pageIndex=" + tiledPageIndex);
                                                    if (tiledPageIndex != -1) {
                                                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(tiledPageIndex)).dropTile(customTileInfo, Boolean.TRUE);
                                                        break;
                                                    }
                                                }
                                            } else {
                                                CustomizerTileViewPager.CustomizerTilePage customizerTilePage = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                                                int indexOf = customizerTilePage.indexOf(customTileInfo);
                                                if (indexOf >= 0) {
                                                    if (indexOf >= customizerTilePage.mCustomTilesInfo.size()) {
                                                        indexOf = customizerTilePage.mCustomTilesInfo.size() - 1;
                                                    }
                                                    SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(indexOf)).customTileView;
                                                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("selectTileByAccessibility position = ", indexOf, "CustomizerTileLayout");
                                                    secCustomizeTileView.mLabel.setAlpha(0.0f);
                                                    break;
                                                }
                                            }
                                        } else {
                                            ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).selectTile(customTileInfo, false);
                                            break;
                                        }
                                    } else {
                                        customizerTileViewPager.movePage(messageObjectAnim, currentItem);
                                        break;
                                    }
                                } else {
                                    ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).dropTile(customTileInfo, Boolean.FALSE);
                                    customizerTileViewPager.mLongClickedViewInfo = null;
                                    break;
                                }
                            } else {
                                final CustomizerTileViewPager.CustomizerTilePage customizerTilePage2 = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                                int indexOf2 = customizerTilePage2.indexOf(customTileInfo);
                                if (indexOf2 >= 0) {
                                    int min = Math.min(customizerTilePage2.mColumns * customizerTilePage2.mMaxRows, customizerTilePage2.mCustomTilesInfo.size());
                                    int i3 = min - 1;
                                    if (i > i3) {
                                        i = i3;
                                    }
                                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("moveTile from = ", indexOf2, " to  = ", i, "total = ");
                                    m45m.append(min);
                                    m45m.append("fromtileInfo = ");
                                    m45m.append(customTileInfo.spec);
                                    Log.d("CustomizerTileLayout", m45m.toString());
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    if (indexOf2 < i) {
                                        int i4 = indexOf2;
                                        while (i4 < i) {
                                            int i5 = i4 + 1;
                                            SecCustomizeTileView secCustomizeTileView2 = ((SecQSCustomizerBase.CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(i5)).customTileView;
                                            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "x", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i5)).getLeft(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i4)).getLeft()));
                                            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "y", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i5)).getTop(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i4)).getTop()));
                                            i4 = i5;
                                        }
                                    } else {
                                        int i6 = indexOf2;
                                        while (i6 > i) {
                                            int i7 = i6 - 1;
                                            SecCustomizeTileView secCustomizeTileView3 = ((SecQSCustomizerBase.CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(i7)).customTileView;
                                            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView3, "x", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i7)).getLeft(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i6)).getLeft()));
                                            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView3, "y", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i7)).getTop(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i6)).getTop()));
                                            i6 = i7;
                                        }
                                    }
                                    animatorSet.setDuration(200L);
                                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.3
                                        public final /* synthetic */ int val$emptyPos;
                                        public final /* synthetic */ SecQSCustomizerBase.CustomTileInfo val$fromtileInfo;

                                        public C21283(final int i8, final SecQSCustomizerBase.CustomTileInfo customTileInfo2) {
                                            r2 = i8;
                                            r3 = customTileInfo2;
                                        }

                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationStart(Animator animator) {
                                            Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                                            CustomizerTileLayout customizerTileLayout = CustomizerTileLayout.this;
                                            int i8 = r2;
                                            boolean z2 = r3.isActive;
                                            int i9 = CustomizerTileLayout.$r8$clinit;
                                            customizerTileLayout.setCircleTranslation(i8, z2);
                                        }
                                    });
                                    animatorSet.start();
                                    SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(indexOf2);
                                    customizerTilePage2.mCustomTilesInfo.remove(indexOf2);
                                    customizerTilePage2.mCustomTilesInfo.add(i8, customTileInfo2);
                                    break;
                                }
                            }
                        } else {
                            Log.e("CSTMPagedTileLayout", "TileInfo is null");
                            break;
                        }
                        break;
                    case 103:
                        secQSCustomizerController.moveToArea((SecQSCustomizerBase.MessageObjectAnim) message.obj);
                        break;
                }
            }
        };
        this.mClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                if (secQSCustomizerController.mIsReadyToClick) {
                    final int i = 0;
                    secQSCustomizerController.mIsReadyToClick = false;
                    int i2 = SecQSSettingEditResources.LARGE_POS;
                    if (view.getId() == SecQSSettingEditResources.REMOVE_ICON_ID) {
                        view = (View) view.getTag();
                    }
                    int id = ((View) view.getParent().getParent()).getId();
                    final SecQSCustomizerBase.CustomTileInfo customTileInfo = (SecQSCustomizerBase.CustomTileInfo) view.getTag();
                    SecQSCustomizerController.HandlerC21499 handlerC21499 = secQSCustomizerController.mHandler;
                    final int i3 = 1;
                    if (id == R.id.qs_customizer_active_pager) {
                        secQSCustomizerController.animateArea(customTileInfo, 1000, i2 * 2);
                        handlerC21499.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i) {
                                    case 0:
                                        secQSCustomizerController.animationDropOtherPage(customTileInfo);
                                        break;
                                    default:
                                        secQSCustomizerController.animationDrop(customTileInfo);
                                        break;
                                }
                            }
                        }, 100L);
                    } else if (id == R.id.qs_customizer_available_pager) {
                        secQSCustomizerController.animateArea(customTileInfo, 2000, i2);
                        handlerC21499.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        secQSCustomizerController.animationDropOtherPage(customTileInfo);
                                        break;
                                    default:
                                        secQSCustomizerController.animationDrop(customTileInfo);
                                        break;
                                }
                            }
                        }, 100L);
                    }
                    handlerC21499.postDelayed(new SecQSCustomizerController$$ExternalSyntheticLambda2(secQSCustomizerController, 1), 500L);
                }
            }
        };
        ((SecQSCustomizerBase) this.mView).mEditResources = secQSSettingEditResources;
        this.mContext = getContext();
        this.mResetButton = ((SecQSCustomizerBase) this.mView).findViewById(R.id.reset_button);
        this.mDoneButton = ((SecQSCustomizerBase) this.mView).findViewById(R.id.done_button);
        SecQSCustomizerAnimator secQSCustomizerAnimator = new SecQSCustomizerAnimator((SecQSCustomizerBase) this.mView);
        this.mAnimator = secQSCustomizerAnimator;
        Iterator it = secQSCustomizerAnimator.mActiveContents.iterator();
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) it;
            if (!itr.hasNext()) {
                break;
            } else {
                ((View) itr.next()).setAlpha(0.0f);
            }
        }
        Iterator it2 = secQSCustomizerAnimator.mAvailableAreaContents.iterator();
        while (true) {
            ListBuilder.Itr itr2 = (ListBuilder.Itr) it2;
            if (!itr2.hasNext()) {
                break;
            }
            View view = (View) itr2.next();
            view.setTranslationY(secQSCustomizerAnimator.startY);
            view.setAlpha(0.0f);
        }
        View findViewById = secQSCustomizerAnimator.mView.findViewById(R.id.qs_customizer_top_summary);
        findViewById.setScaleY(0.95f);
        findViewById.setScaleX(0.95f);
        this.mIsTopEdit = z;
        this.mEditResources = secQSSettingEditResources;
        CustomizerTileViewPager customizerTileViewPager = (CustomizerTileViewPager) ((SecQSCustomizerBase) this.mView).findViewById(R.id.qs_customizer_active_pager);
        this.mActiveTileLayout = customizerTileViewPager;
        customizerTileViewPager.mAnimator = this.mAnimator;
        customizerTileViewPager.mIsTopEdit = z;
        CustomizerTileViewPager customizerTileViewPager2 = (CustomizerTileViewPager) ((SecQSCustomizerBase) this.mView).findViewById(R.id.qs_customizer_available_pager);
        this.mAvailableTileLayout = customizerTileViewPager2;
        customizerTileViewPager2.mAnimator = this.mAnimator;
        customizerTileViewPager2.mIsTopEdit = z;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context = ((SecQSCustomizerBase) this.mView).getContext();
        secQSPanelResourcePicker.getClass();
        this.mTopMinMaxNum = SecQSPanelResourcePicker.getQsTileMinNum(context);
        this.mAudioManager = (AudioManager) getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = z ? secQSSettingEditResources.tileTopAdapter : secQSSettingEditResources.tileFullAdapter;
        this.mTileAdapter = secQSCustomizerTileAdapter2;
        secQSCustomizerTileAdapter2.getClass();
        customizerTileViewPager.mInitialPagenum = secQSCustomizerTileAdapter2.mActiveCurrentPage;
        customizerTileViewPager2.mInitialPagenum = secQSCustomizerTileAdapter2.mAvailableCurrentPage;
        ((SecQSCustomizerBase) this.mView).updateResources();
        ArrayList arrayList = secQSCustomizerTileAdapter2.mActiveTiles;
        ViewOnLongClickListenerC21466 viewOnLongClickListenerC21466 = this.mLongClickListener;
        if (arrayList != null) {
            customizerTileViewPager.addTiles(arrayList);
            Iterator it3 = secQSCustomizerTileAdapter2.mActiveTiles.iterator();
            while (it3.hasNext()) {
                ((SecQSCustomizerBase.CustomTileInfo) it3.next()).longClickListener = viewOnLongClickListenerC21466;
            }
        }
        SecQSSettingEditResources secQSSettingEditResources2 = this.mEditResources;
        if (((!this.mIsTopEdit ? (secQSCustomizerTileAdapter = secQSSettingEditResources2.tileFullAdapter) != null : (secQSCustomizerTileAdapter = secQSSettingEditResources2.tileTopAdapter) != null) ? null : Boolean.valueOf(secQSCustomizerTileAdapter.mIsLoadedAllTiles)).booleanValue()) {
            ArrayList arrayList2 = secQSCustomizerTileAdapter2.mAvailableTiles;
            if (arrayList2 != null) {
                secQSCustomizerTileAdapter2.mOnTileChangedCallback = null;
                this.mAvailableTileLayout.addTiles(arrayList2);
                Iterator it4 = secQSCustomizerTileAdapter2.mAvailableTiles.iterator();
                while (it4.hasNext()) {
                    ((SecQSCustomizerBase.CustomTileInfo) it4.next()).longClickListener = viewOnLongClickListenerC21466;
                }
            }
        } else {
            secQSCustomizerTileAdapter2.mOnTileChangedCallback = new SecQSCustomizerController$$ExternalSyntheticLambda6(this);
        }
        List list = secQSCustomizerTileAdapter2.mAllTiles;
        int size = list != null ? list.size() : -1;
        SecQSCustomizerBase secQSCustomizerBase2 = (SecQSCustomizerBase) this.mView;
        int i = (size / (secQSCustomizerBase2.mActiveRows * secQSCustomizerBase2.mActiveColumns)) + 1;
        customizerTileViewPager.setOffscreenPageLimit(i <= 0 ? 3 : i);
    }

    public final void animateArea(SecQSCustomizerBase.CustomTileInfo customTileInfo, int i, int i2) {
        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
        messageObjectAnim.animationType = i;
        messageObjectAnim.touchedPos = i2;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        HandlerC21499 handlerC21499 = this.mHandler;
        handlerC21499.sendMessageDelayed(handlerC21499.obtainMessage(103, messageObjectAnim), 100L);
    }

    public final void animateCurrentPage(SecQSCustomizerBase.CustomTileInfo customTileInfo, int i) {
        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
        messageObjectAnim.touchedPos = i;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        messageObjectAnim.animationType = 202;
        HandlerC21499 handlerC21499 = this.mHandler;
        if (handlerC21499.hasMessages(102)) {
            handlerC21499.removeMessages(102);
        }
        handlerC21499.sendMessage(handlerC21499.obtainMessage(102, messageObjectAnim));
    }

    public final void animationDrop(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
        messageObjectAnim.animationType = 201;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        HandlerC21499 handlerC21499 = this.mHandler;
        if (handlerC21499.hasMessages(101)) {
            handlerC21499.removeMessages(101);
        }
        handlerC21499.sendMessageDelayed(handlerC21499.obtainMessage(101, messageObjectAnim), this.mIsReadyToMove ? 0L : 200L);
    }

    public final void animationDropOtherPage(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
        messageObjectAnim.animationType = 210;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        HandlerC21499 handlerC21499 = this.mHandler;
        if (handlerC21499.hasMessages(101)) {
            handlerC21499.removeMessages(101);
        }
        handlerC21499.sendMessage(handlerC21499.obtainMessage(101, messageObjectAnim));
    }

    public final void animationStart(SecQSCustomizerBase.CustomTileInfo customTileInfo, Boolean bool) {
        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
        messageObjectAnim.animationType = bool.booleanValue() ? IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState : 200;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        HandlerC21499 handlerC21499 = this.mHandler;
        if (handlerC21499.hasMessages(100)) {
            handlerC21499.removeMessages(100);
        }
        handlerC21499.sendMessage(handlerC21499.obtainMessage(100, messageObjectAnim));
        SecCustomizeTileView secCustomizeTileView = customTileInfo.customTileView;
        StringBuilder sb = new StringBuilder();
        Context context = this.mContext;
        sb.append(String.format(context.getString(R.string.qs_custom_action_long_pressed), customTileInfo.customTileView.mLabel.getText()));
        sb.append(context.getString(R.string.qs_custom_action_drag_to_move));
        secCustomizeTileView.announceForAccessibility(sb.toString());
    }

    public final void close() {
        if (((SecQSCustomizerBase) this.mView).isShown()) {
            startClosingAnim();
            CustomActionMoveItem customActionMoveItem = this.mCustomActionMoveItem;
            if (customActionMoveItem != null) {
                customActionMoveItem.actionFinish();
            }
            save();
            SecQSCustomizerBase secQSCustomizerBase = (SecQSCustomizerBase) this.mView;
            if (secQSCustomizerBase.isShown) {
                Log.d("SecQSCustomizerBase", "close customizer");
                secQSCustomizerBase.isShown = false;
                SystemUIAnalytics.sendScreenViewLog("QPP101");
            }
            SystemUIAnalytics.sendScreenViewLog("QPP101");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final CustomActionMoveItem createCustomActionMoveItem(SecQSCustomizerBase.CustomTileInfo customTileInfo, final CustomizerTileViewPager customizerTileViewPager, final CustomizerTileViewPager customizerTileViewPager2) {
        ArrayList tilesInfo = customizerTileViewPager.getTilesInfo();
        ArrayList tilesInfo2 = customizerTileViewPager2.getTilesInfo();
        if (!tilesInfo.contains(customTileInfo) && !tilesInfo2.contains(customTileInfo)) {
            return null;
        }
        final int i = 2;
        final int i2 = 0;
        if (tilesInfo.contains(customTileInfo)) {
            this.mWhereAmI = PluginEdgeLightingPlus.VERSION;
            final boolean z = !this.mIsTopEdit || customizerTileViewPager2.getMinimumTileNum() < this.mTopMinMaxNum;
            return new CustomActionMoveItem(this.mContext, customTileInfo, customizerTileViewPager, customizerTileViewPager2, this.mWhereAmI == 6000, new BiConsumer(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda3
                public final /* synthetic */ SecQSCustomizerController f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    switch (i2) {
                        case 0:
                            SecQSCustomizerController secQSCustomizerController = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager3 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            Integer num = (Integer) obj2;
                            secQSCustomizerController.getClass();
                            Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + num);
                            Integer valueOf = Integer.valueOf(num.intValue() % (customizerTileViewPager3.mColumns * customizerTileViewPager3.mRows));
                            secQSCustomizerController.animationStart(customTileInfo2, Boolean.TRUE);
                            secQSCustomizerController.animateCurrentPage(customTileInfo2, valueOf.intValue());
                            break;
                        case 1:
                            SecQSCustomizerController secQSCustomizerController2 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager4 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo3 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            secQSCustomizerController2.getClass();
                            customizerTileViewPager4.moveTile(customTileInfo3, ((Integer) obj2).intValue());
                            secQSCustomizerController2.animationDrop(customTileInfo3);
                            secQSCustomizerController2.sendAnnouncementEvent();
                            break;
                        case 2:
                            SecQSCustomizerController secQSCustomizerController3 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager5 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo4 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            Integer num2 = (Integer) obj2;
                            secQSCustomizerController3.getClass();
                            Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo4.customTileView.mLabel.getText()) + ", startIndex=" + num2);
                            Integer valueOf2 = Integer.valueOf(num2.intValue() % (customizerTileViewPager5.mColumns * customizerTileViewPager5.mRows));
                            secQSCustomizerController3.animationStart(customTileInfo4, Boolean.TRUE);
                            secQSCustomizerController3.animateCurrentPage(customTileInfo4, valueOf2.intValue());
                            break;
                        case 3:
                            SecQSCustomizerController secQSCustomizerController4 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager6 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo5 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            secQSCustomizerController4.getClass();
                            customizerTileViewPager6.moveTile(customTileInfo5, ((Integer) obj2).intValue());
                            secQSCustomizerController4.animationDrop(customTileInfo5);
                            secQSCustomizerController4.sendAnnouncementEvent();
                            break;
                        default:
                            SecQSCustomizerController secQSCustomizerController5 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager7 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo6 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            secQSCustomizerController5.getClass();
                            int intValue = Integer.valueOf(((Integer) obj2).intValue() % (customizerTileViewPager7.mColumns * customizerTileViewPager7.mRows)).intValue();
                            SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                            messageObjectAnim.animationType = 1000;
                            messageObjectAnim.touchedPos = intValue;
                            messageObjectAnim.longClickedTileInfo = customTileInfo6;
                            secQSCustomizerController5.moveToArea(messageObjectAnim);
                            secQSCustomizerController5.animationDrop(customTileInfo6);
                            secQSCustomizerController5.sendAnnouncementEvent();
                            break;
                    }
                }
            }, new SecQSCustomizerController$$ExternalSyntheticLambda1(this, i), new BiConsumer(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda3
                public final /* synthetic */ SecQSCustomizerController f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    switch (r3) {
                        case 0:
                            SecQSCustomizerController secQSCustomizerController = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager3 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            Integer num = (Integer) obj2;
                            secQSCustomizerController.getClass();
                            Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + num);
                            Integer valueOf = Integer.valueOf(num.intValue() % (customizerTileViewPager3.mColumns * customizerTileViewPager3.mRows));
                            secQSCustomizerController.animationStart(customTileInfo2, Boolean.TRUE);
                            secQSCustomizerController.animateCurrentPage(customTileInfo2, valueOf.intValue());
                            break;
                        case 1:
                            SecQSCustomizerController secQSCustomizerController2 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager4 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo3 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            secQSCustomizerController2.getClass();
                            customizerTileViewPager4.moveTile(customTileInfo3, ((Integer) obj2).intValue());
                            secQSCustomizerController2.animationDrop(customTileInfo3);
                            secQSCustomizerController2.sendAnnouncementEvent();
                            break;
                        case 2:
                            SecQSCustomizerController secQSCustomizerController3 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager5 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo4 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            Integer num2 = (Integer) obj2;
                            secQSCustomizerController3.getClass();
                            Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo4.customTileView.mLabel.getText()) + ", startIndex=" + num2);
                            Integer valueOf2 = Integer.valueOf(num2.intValue() % (customizerTileViewPager5.mColumns * customizerTileViewPager5.mRows));
                            secQSCustomizerController3.animationStart(customTileInfo4, Boolean.TRUE);
                            secQSCustomizerController3.animateCurrentPage(customTileInfo4, valueOf2.intValue());
                            break;
                        case 3:
                            SecQSCustomizerController secQSCustomizerController4 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager6 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo5 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            secQSCustomizerController4.getClass();
                            customizerTileViewPager6.moveTile(customTileInfo5, ((Integer) obj2).intValue());
                            secQSCustomizerController4.animationDrop(customTileInfo5);
                            secQSCustomizerController4.sendAnnouncementEvent();
                            break;
                        default:
                            SecQSCustomizerController secQSCustomizerController5 = this.f$0;
                            CustomizerTileViewPager customizerTileViewPager7 = customizerTileViewPager;
                            SecQSCustomizerBase.CustomTileInfo customTileInfo6 = (SecQSCustomizerBase.CustomTileInfo) obj;
                            secQSCustomizerController5.getClass();
                            int intValue = Integer.valueOf(((Integer) obj2).intValue() % (customizerTileViewPager7.mColumns * customizerTileViewPager7.mRows)).intValue();
                            SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                            messageObjectAnim.animationType = 1000;
                            messageObjectAnim.touchedPos = intValue;
                            messageObjectAnim.longClickedTileInfo = customTileInfo6;
                            secQSCustomizerController5.moveToArea(messageObjectAnim);
                            secQSCustomizerController5.animationDrop(customTileInfo6);
                            secQSCustomizerController5.sendAnnouncementEvent();
                            break;
                    }
                }
            }, new BiConsumer() { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                    CustomizerTileViewPager customizerTileViewPager3 = customizerTileViewPager2;
                    boolean z2 = z;
                    SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) obj;
                    secQSCustomizerController.getClass();
                    int intValue = Integer.valueOf(((Integer) obj2).intValue() % (customizerTileViewPager3.mColumns * customizerTileViewPager3.mRows)).intValue();
                    SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                    messageObjectAnim.animationType = 2000;
                    messageObjectAnim.touchedPos = intValue;
                    messageObjectAnim.longClickedTileInfo = customTileInfo2;
                    secQSCustomizerController.moveToArea(messageObjectAnim);
                    secQSCustomizerController.animationDrop(customTileInfo2);
                    if (z2) {
                        secQSCustomizerController.sendAnnouncementEvent();
                    }
                }
            }, z);
        }
        this.mWhereAmI = 5000;
        Context context = this.mContext;
        r7 = this.mWhereAmI != 6000 ? 0 : 1;
        BiConsumer biConsumer = new BiConsumer(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda3
            public final /* synthetic */ SecQSCustomizerController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        SecQSCustomizerController secQSCustomizerController = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager3 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        Integer num = (Integer) obj2;
                        secQSCustomizerController.getClass();
                        Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + num);
                        Integer valueOf = Integer.valueOf(num.intValue() % (customizerTileViewPager3.mColumns * customizerTileViewPager3.mRows));
                        secQSCustomizerController.animationStart(customTileInfo2, Boolean.TRUE);
                        secQSCustomizerController.animateCurrentPage(customTileInfo2, valueOf.intValue());
                        break;
                    case 1:
                        SecQSCustomizerController secQSCustomizerController2 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager4 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo3 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController2.getClass();
                        customizerTileViewPager4.moveTile(customTileInfo3, ((Integer) obj2).intValue());
                        secQSCustomizerController2.animationDrop(customTileInfo3);
                        secQSCustomizerController2.sendAnnouncementEvent();
                        break;
                    case 2:
                        SecQSCustomizerController secQSCustomizerController3 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager5 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo4 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        Integer num2 = (Integer) obj2;
                        secQSCustomizerController3.getClass();
                        Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo4.customTileView.mLabel.getText()) + ", startIndex=" + num2);
                        Integer valueOf2 = Integer.valueOf(num2.intValue() % (customizerTileViewPager5.mColumns * customizerTileViewPager5.mRows));
                        secQSCustomizerController3.animationStart(customTileInfo4, Boolean.TRUE);
                        secQSCustomizerController3.animateCurrentPage(customTileInfo4, valueOf2.intValue());
                        break;
                    case 3:
                        SecQSCustomizerController secQSCustomizerController4 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager6 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo5 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController4.getClass();
                        customizerTileViewPager6.moveTile(customTileInfo5, ((Integer) obj2).intValue());
                        secQSCustomizerController4.animationDrop(customTileInfo5);
                        secQSCustomizerController4.sendAnnouncementEvent();
                        break;
                    default:
                        SecQSCustomizerController secQSCustomizerController5 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager7 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo6 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController5.getClass();
                        int intValue = Integer.valueOf(((Integer) obj2).intValue() % (customizerTileViewPager7.mColumns * customizerTileViewPager7.mRows)).intValue();
                        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                        messageObjectAnim.animationType = 1000;
                        messageObjectAnim.touchedPos = intValue;
                        messageObjectAnim.longClickedTileInfo = customTileInfo6;
                        secQSCustomizerController5.moveToArea(messageObjectAnim);
                        secQSCustomizerController5.animationDrop(customTileInfo6);
                        secQSCustomizerController5.sendAnnouncementEvent();
                        break;
                }
            }
        };
        final int i3 = 3;
        SecQSCustomizerController$$ExternalSyntheticLambda1 secQSCustomizerController$$ExternalSyntheticLambda1 = new SecQSCustomizerController$$ExternalSyntheticLambda1(this, i3);
        BiConsumer biConsumer2 = new BiConsumer(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda3
            public final /* synthetic */ SecQSCustomizerController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i3) {
                    case 0:
                        SecQSCustomizerController secQSCustomizerController = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager3 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        Integer num = (Integer) obj2;
                        secQSCustomizerController.getClass();
                        Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + num);
                        Integer valueOf = Integer.valueOf(num.intValue() % (customizerTileViewPager3.mColumns * customizerTileViewPager3.mRows));
                        secQSCustomizerController.animationStart(customTileInfo2, Boolean.TRUE);
                        secQSCustomizerController.animateCurrentPage(customTileInfo2, valueOf.intValue());
                        break;
                    case 1:
                        SecQSCustomizerController secQSCustomizerController2 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager4 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo3 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController2.getClass();
                        customizerTileViewPager4.moveTile(customTileInfo3, ((Integer) obj2).intValue());
                        secQSCustomizerController2.animationDrop(customTileInfo3);
                        secQSCustomizerController2.sendAnnouncementEvent();
                        break;
                    case 2:
                        SecQSCustomizerController secQSCustomizerController3 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager5 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo4 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        Integer num2 = (Integer) obj2;
                        secQSCustomizerController3.getClass();
                        Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo4.customTileView.mLabel.getText()) + ", startIndex=" + num2);
                        Integer valueOf2 = Integer.valueOf(num2.intValue() % (customizerTileViewPager5.mColumns * customizerTileViewPager5.mRows));
                        secQSCustomizerController3.animationStart(customTileInfo4, Boolean.TRUE);
                        secQSCustomizerController3.animateCurrentPage(customTileInfo4, valueOf2.intValue());
                        break;
                    case 3:
                        SecQSCustomizerController secQSCustomizerController4 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager6 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo5 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController4.getClass();
                        customizerTileViewPager6.moveTile(customTileInfo5, ((Integer) obj2).intValue());
                        secQSCustomizerController4.animationDrop(customTileInfo5);
                        secQSCustomizerController4.sendAnnouncementEvent();
                        break;
                    default:
                        SecQSCustomizerController secQSCustomizerController5 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager7 = customizerTileViewPager2;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo6 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController5.getClass();
                        int intValue = Integer.valueOf(((Integer) obj2).intValue() % (customizerTileViewPager7.mColumns * customizerTileViewPager7.mRows)).intValue();
                        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                        messageObjectAnim.animationType = 1000;
                        messageObjectAnim.touchedPos = intValue;
                        messageObjectAnim.longClickedTileInfo = customTileInfo6;
                        secQSCustomizerController5.moveToArea(messageObjectAnim);
                        secQSCustomizerController5.animationDrop(customTileInfo6);
                        secQSCustomizerController5.sendAnnouncementEvent();
                        break;
                }
            }
        };
        final int i4 = 4;
        return new CustomActionMoveItem(context, customTileInfo, customizerTileViewPager2, customizerTileViewPager, r7, biConsumer, secQSCustomizerController$$ExternalSyntheticLambda1, biConsumer2, new BiConsumer(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController$$ExternalSyntheticLambda3
            public final /* synthetic */ SecQSCustomizerController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i4) {
                    case 0:
                        SecQSCustomizerController secQSCustomizerController = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager3 = customizerTileViewPager;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        Integer num = (Integer) obj2;
                        secQSCustomizerController.getClass();
                        Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo2.customTileView.mLabel.getText()) + ", startIndex=" + num);
                        Integer valueOf = Integer.valueOf(num.intValue() % (customizerTileViewPager3.mColumns * customizerTileViewPager3.mRows));
                        secQSCustomizerController.animationStart(customTileInfo2, Boolean.TRUE);
                        secQSCustomizerController.animateCurrentPage(customTileInfo2, valueOf.intValue());
                        break;
                    case 1:
                        SecQSCustomizerController secQSCustomizerController2 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager4 = customizerTileViewPager;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo3 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController2.getClass();
                        customizerTileViewPager4.moveTile(customTileInfo3, ((Integer) obj2).intValue());
                        secQSCustomizerController2.animationDrop(customTileInfo3);
                        secQSCustomizerController2.sendAnnouncementEvent();
                        break;
                    case 2:
                        SecQSCustomizerController secQSCustomizerController3 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager5 = customizerTileViewPager;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo4 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        Integer num2 = (Integer) obj2;
                        secQSCustomizerController3.getClass();
                        Log.d("SecQSCustomizerController", "sourceTile=" + ((Object) customTileInfo4.customTileView.mLabel.getText()) + ", startIndex=" + num2);
                        Integer valueOf2 = Integer.valueOf(num2.intValue() % (customizerTileViewPager5.mColumns * customizerTileViewPager5.mRows));
                        secQSCustomizerController3.animationStart(customTileInfo4, Boolean.TRUE);
                        secQSCustomizerController3.animateCurrentPage(customTileInfo4, valueOf2.intValue());
                        break;
                    case 3:
                        SecQSCustomizerController secQSCustomizerController4 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager6 = customizerTileViewPager;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo5 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController4.getClass();
                        customizerTileViewPager6.moveTile(customTileInfo5, ((Integer) obj2).intValue());
                        secQSCustomizerController4.animationDrop(customTileInfo5);
                        secQSCustomizerController4.sendAnnouncementEvent();
                        break;
                    default:
                        SecQSCustomizerController secQSCustomizerController5 = this.f$0;
                        CustomizerTileViewPager customizerTileViewPager7 = customizerTileViewPager;
                        SecQSCustomizerBase.CustomTileInfo customTileInfo6 = (SecQSCustomizerBase.CustomTileInfo) obj;
                        secQSCustomizerController5.getClass();
                        int intValue = Integer.valueOf(((Integer) obj2).intValue() % (customizerTileViewPager7.mColumns * customizerTileViewPager7.mRows)).intValue();
                        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                        messageObjectAnim.animationType = 1000;
                        messageObjectAnim.touchedPos = intValue;
                        messageObjectAnim.longClickedTileInfo = customTileInfo6;
                        secQSCustomizerController5.moveToArea(messageObjectAnim);
                        secQSCustomizerController5.animationDrop(customTileInfo6);
                        secQSCustomizerController5.sendAnnouncementEvent();
                        break;
                }
            }
        }, true);
    }

    public final void moveToArea(SecQSCustomizerBase.MessageObjectAnim messageObjectAnim) {
        SecQSCustomizerBase.CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
        int i = messageObjectAnim.animationType;
        StringBuilder sb = new StringBuilder(", ");
        Context context = this.mContext;
        String m73m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder, sb);
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = this.mTileAdapter;
        CustomizerTileViewPager customizerTileViewPager = this.mAvailableTileLayout;
        CustomizerTileViewPager customizerTileViewPager2 = this.mActiveTileLayout;
        if (i == 1000) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(context.getString(R.string.qs_edit_setting_available_area_tapped));
            sb2.append(" ");
            sb2.append((Object) customTileInfo.state.label);
            customTileInfo.customizeTileContentDes = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb2, customTileInfo.isNewCustomTile ? KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.controls_badge_description, new StringBuilder(", ")) : "", m73m);
            customTileInfo.isActive = false;
            customizerTileViewPager2.removeTile(customTileInfo);
            customizerTileViewPager.addTile(customTileInfo, messageObjectAnim.touchedPos);
            secQSCustomizerTileAdapter.updateRemovedTileList(customTileInfo.spec, false);
        } else {
            if (this.mIsTopEdit) {
                int minimumTileNum = customizerTileViewPager2.getMinimumTileNum();
                int i2 = this.mTopMinMaxNum;
                if (minimumTileNum >= i2) {
                    if (!(customizerTileViewPager2.mDummyTile != null)) {
                        SecQSCustomizerBase secQSCustomizerBase = (SecQSCustomizerBase) this.mView;
                        secQSCustomizerBase.mMinNum = i2;
                        Resources resources = secQSCustomizerBase.mContext.getResources();
                        int i3 = secQSCustomizerBase.mMinNum;
                        String quantityString = resources.getQuantityString(R.plurals.sec_qs_unable_add_maximum, i3, Integer.valueOf(i3));
                        if (secQSCustomizerBase.mToast == null) {
                            secQSCustomizerBase.mToast = Toast.makeText(secQSCustomizerBase.mContext, "", 0);
                        }
                        secQSCustomizerBase.mToast.setText(quantityString);
                        secQSCustomizerBase.mToast.show();
                        return;
                    }
                }
            }
            customTileInfo.customizeTileContentDes = context.getString(R.string.qs_edit_setting_active_area_tapped) + " " + ((Object) customTileInfo.state.label) + m73m;
            customTileInfo.isActive = true;
            customizerTileViewPager2.addTile(customTileInfo, messageObjectAnim.touchedPos);
            customizerTileViewPager.removeTile(customTileInfo);
            secQSCustomizerTileAdapter.updateRemovedTileList(customTileInfo.spec, true);
        }
        (this.mWhereAmI == 5000 ? customizerTileViewPager2 : customizerTileViewPager).mLongClickedViewInfo = null;
        int i4 = i == 1000 ? PluginEdgeLightingPlus.VERSION : 5000;
        this.mWhereAmI = i4;
        if (i4 == 5000) {
            customizerTileViewPager = customizerTileViewPager2;
        }
        customizerTileViewPager.mLongClickedViewInfo = this.mLongClickedViewInfo;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mResetButton.setOnClickListener(this.mResetOnClickListener);
        this.mDoneButton.setOnClickListener(this.mDoneOnClickListener);
        this.mCurrentOrientation = this.mContext.getResources().getConfiguration().orientation;
        setupPager();
        setUpPageArea();
        SecQSCustomizerAnimator secQSCustomizerAnimator = this.mAnimator;
        Iterator it = secQSCustomizerAnimator.mActiveContents.iterator();
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) it;
            if (!itr.hasNext()) {
                break;
            } else {
                ((View) itr.next()).animate().alpha(1.0f).setDuration(100L).start();
            }
        }
        Iterator it2 = secQSCustomizerAnimator.mAvailableAreaContents.iterator();
        while (true) {
            ListBuilder.Itr itr2 = (ListBuilder.Itr) it2;
            if (!itr2.hasNext()) {
                secQSCustomizerAnimator.mView.findViewById(R.id.qs_customizer_top_summary).animate().scaleY(1.0f).scaleX(1.0f).setDuration(200L).start();
                return;
            }
            ((View) itr2.next()).animate().translationY(0.0f).alpha(1.0f).setDuration(200L).start();
        }
    }

    public final void removeAreaAnimationMessage() {
        HandlerC21499 handlerC21499 = this.mHandler;
        if (handlerC21499.hasMessages(103)) {
            handlerC21499.removeMessages(103);
        }
    }

    public final void save() {
        String str;
        String str2;
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = this.mTileAdapter;
        if (secQSCustomizerTileAdapter.mTileQueryHelper.mFinished) {
            Log.d("SecQSCustomizerTileAdapter", "mCurrentSpecs =  " + secQSCustomizerTileAdapter.mCurrentSpecs);
            CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
            List spec = customizerTileViewPager.getSpec();
            if (((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).equals(spec)) {
                Log.d("SecQSCustomizerTileAdapter", "save none : same list");
                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1022", "isChanged", "false", "QUICK_PANEL_BUTTON");
                return;
            }
            SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1022", "isChanged", "true", "QUICK_PANEL_BUTTON");
            QSTileHost qSTileHost = secQSCustomizerTileAdapter.mHost;
            boolean z = secQSCustomizerTileAdapter.mIsTopEdit;
            Context context = secQSCustomizerTileAdapter.mContext;
            if (z) {
                Prefs.putBoolean(context, "QQsHasEditedQuickTileList", true);
                secQSCustomizerTileAdapter.mQQSHost.changeTiles(spec);
            } else {
                Prefs.putBoolean(context, "QsHasEditedQuickTileList", true);
                qSTileHost.changeTilesByUser(secQSCustomizerTileAdapter.mCurrentSpecs, spec);
            }
            secQSCustomizerTileAdapter.mDefaultActiveTiles = customizerTileViewPager.getTilesInfo();
            CustomizerTileViewPager customizerTileViewPager2 = this.mAvailableTileLayout;
            secQSCustomizerTileAdapter.mDefaultAvailableTiles = customizerTileViewPager2.getTilesInfo();
            secQSCustomizerTileAdapter.saveTiles(customizerTileViewPager, customizerTileViewPager2, true);
            ArrayList arrayList = (ArrayList) spec;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str3 = (String) it.next();
                if (!((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).contains(str3)) {
                    if (str3.startsWith("custom(")) {
                        str2 = qSTileHost.getCustomTileNameFromSpec(str3);
                        if (str2 == null) {
                            str2 = CustomTile.getComponentFromSpec(str3).toShortString();
                        }
                    } else {
                        str2 = str3;
                    }
                    String valueOf = String.valueOf(arrayList.indexOf(str3) + 1);
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1023", "buttonName", str2, "position", valueOf);
                    CustomizationProvider$$ExternalSyntheticOutline0.m135m("save add : ", str2, " ", valueOf, "SecQSCustomizerTileAdapter");
                }
            }
            Iterator it2 = ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).iterator();
            while (it2.hasNext()) {
                String str4 = (String) it2.next();
                if (str4.startsWith("custom(")) {
                    str = qSTileHost.getCustomTileNameFromSpec(str4);
                    if (str == null) {
                        str = CustomTile.getComponentFromSpec(str4).toShortString();
                    }
                } else {
                    str = str4;
                }
                if (!arrayList.contains(str4)) {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1024", "buttonName", str);
                    Log.d("SecQSCustomizerTileAdapter", "save remove : " + str);
                }
            }
            secQSCustomizerTileAdapter.mCurrentSpecs = spec;
        }
    }

    public final void sendAnnouncementEvent() {
        Context context = this.mContext;
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain(16384);
        obtain.getText().clear();
        obtain.getText().add(context.getResources().getString(R.string.qs_custom_action_move_done));
        obtain.setPackageName(context.getPackageName());
        accessibilityManager.sendAccessibilityEvent(obtain);
    }

    public final void setUpPageArea() {
        FrameLayout frameLayout = (FrameLayout) ((SecQSCustomizerBase) this.mView).findViewById(R.id.active_page_area_left);
        ViewOnDragListenerC21488 viewOnDragListenerC21488 = this.mDragListener;
        frameLayout.setOnDragListener(viewOnDragListenerC21488);
        frameLayout.setTag(Integer.valueOf(this.ACTIVE_LEFT_PAGE_AREA));
        frameLayout.bringToFront();
        FrameLayout frameLayout2 = (FrameLayout) ((SecQSCustomizerBase) this.mView).findViewById(R.id.active_page_area_right);
        frameLayout2.setOnDragListener(viewOnDragListenerC21488);
        frameLayout2.setTag(Integer.valueOf(this.ACTIVE_RIGHT_PAGE_AREA));
        frameLayout2.bringToFront();
    }

    public final void setupPager() {
        this.mCustomActionManager = new CustomActionManager();
        updateFont(R.id.qs_edit_summary, R.dimen.qs_edit_summary_text);
        updateFont(R.id.qs_edit_more_summary, R.dimen.qs_edit_summary_text);
        ((TextView) ((SecQSCustomizerBase) this.mView).findViewById(R.id.qs_edit_summary)).setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.10
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
            }
        });
        ((TextView) ((SecQSCustomizerBase) this.mView).findViewById(R.id.qs_edit_more_summary)).setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qs.customize.SecQSCustomizerController.11
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
            }
        });
        SecPageIndicator secPageIndicator = (SecPageIndicator) ((SecQSCustomizerBase) this.mView).findViewById(R.id.qs_active_paged_indicator);
        final CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
        customizerTileViewPager.mPageIndicator = secPageIndicator;
        secPageIndicator.setLocation(customizerTileViewPager.mPageIndicatorPosition);
        SecPageIndicator secPageIndicator2 = customizerTileViewPager.mPageIndicator;
        secPageIndicator2.mQsExpansion = 1.0f;
        secPageIndicator2.mCallback = new SecPageIndicator.SecPageIndicatorCallback() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager.4
            public C21324() {
            }

            @Override // com.android.systemui.qs.SecPageIndicator.SecPageIndicatorCallback
            public final void setViewPageToSelected(int i) {
                CustomizerTileViewPager.this.setCurrentItem(i, true);
            }
        };
        ViewOnDragListenerC21488 viewOnDragListenerC21488 = this.mDragListener;
        customizerTileViewPager.mDragListener = viewOnDragListenerC21488;
        SecQSCustomizerController$$ExternalSyntheticLambda0 secQSCustomizerController$$ExternalSyntheticLambda0 = this.mClickListener;
        customizerTileViewPager.mClickListener = secQSCustomizerController$$ExternalSyntheticLambda0;
        customizerTileViewPager.mCustomActionManager = this.mCustomActionManager;
        updateFont(R.id.qs_edit_available_text, R.dimen.qs_edit_available_text);
        SecPageIndicator secPageIndicator3 = (SecPageIndicator) ((SecQSCustomizerBase) this.mView).findViewById(R.id.qs_available_paged_indicator);
        final CustomizerTileViewPager customizerTileViewPager2 = this.mAvailableTileLayout;
        customizerTileViewPager2.mPageIndicator = secPageIndicator3;
        secPageIndicator3.setLocation(customizerTileViewPager2.mPageIndicatorPosition);
        SecPageIndicator secPageIndicator4 = customizerTileViewPager2.mPageIndicator;
        secPageIndicator4.mQsExpansion = 1.0f;
        secPageIndicator4.mCallback = new SecPageIndicator.SecPageIndicatorCallback() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager.4
            public C21324() {
            }

            @Override // com.android.systemui.qs.SecPageIndicator.SecPageIndicatorCallback
            public final void setViewPageToSelected(int i) {
                CustomizerTileViewPager.this.setCurrentItem(i, true);
            }
        };
        customizerTileViewPager2.mDragListener = viewOnDragListenerC21488;
        customizerTileViewPager2.mClickListener = secQSCustomizerController$$ExternalSyntheticLambda0;
        int color = getContext().getColor(R.color.qs_edit_available_page_indicator_tint_color_selected);
        int color2 = getContext().getColor(R.color.qs_edit_available_page_indicator_tint_color_unselected);
        SecPageIndicator secPageIndicator5 = customizerTileViewPager2.mPageIndicator;
        if (secPageIndicator5 != null) {
            secPageIndicator5.mSelectedColor = color;
            secPageIndicator5.mUnselectedColor = color2;
        }
        CustomActionManager customActionManager = this.mCustomActionManager;
        customizerTileViewPager2.mCustomActionManager = customActionManager;
        CustomActionId customActionId = CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE;
        SecQSCustomizerController$$ExternalSyntheticLambda1 secQSCustomizerController$$ExternalSyntheticLambda1 = new SecQSCustomizerController$$ExternalSyntheticLambda1(this, 0);
        HashMap hashMap = customActionManager.customActions;
        if (!hashMap.containsKey(customActionId)) {
            hashMap.put(customActionId, secQSCustomizerController$$ExternalSyntheticLambda1);
        }
        CustomActionManager customActionManager2 = this.mCustomActionManager;
        CustomActionId customActionId2 = CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE;
        SecQSCustomizerController$$ExternalSyntheticLambda1 secQSCustomizerController$$ExternalSyntheticLambda12 = new SecQSCustomizerController$$ExternalSyntheticLambda1(this, 1);
        HashMap hashMap2 = customActionManager2.customActions;
        if (!hashMap2.containsKey(customActionId2)) {
            hashMap2.put(customActionId2, secQSCustomizerController$$ExternalSyntheticLambda12);
        }
        String string = getResources().getString(R.string.accessibility_button);
        View findViewById = ((SecQSCustomizerBase) this.mView).findViewById(R.id.done_button);
        this.mDoneButton = findViewById;
        ((TextView) findViewById.findViewById(R.id.button_text)).semSetButtonShapeEnabled(true);
        View view = this.mDoneButton;
        StringBuilder sb = new StringBuilder();
        Context context = this.mContext;
        sb.append(context.getString(R.string.quick_settings_done));
        sb.append(",");
        sb.append(string);
        view.setContentDescription(sb.toString());
        View findViewById2 = ((SecQSCustomizerBase) this.mView).findViewById(R.id.reset_button);
        this.mResetButton = findViewById2;
        ((TextView) findViewById2.findViewById(R.id.button_text)).semSetButtonShapeEnabled(true);
        this.mResetButton.setContentDescription(context.getString(R.string.quick_settings_reset) + "," + string);
    }

    public final void startClosingAnim() {
        final SecQSCustomizerController$$ExternalSyntheticLambda2 secQSCustomizerController$$ExternalSyntheticLambda2 = new SecQSCustomizerController$$ExternalSyntheticLambda2(this, 0);
        SecQSCustomizerAnimator secQSCustomizerAnimator = this.mAnimator;
        secQSCustomizerAnimator.getClass();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(secQSCustomizerAnimator.mActiveContents);
        arrayList2.addAll(secQSCustomizerAnimator.mAvailableAreaContents);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder((View) it.next(), PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, secQSCustomizerAnimator.startY));
            ofPropertyValuesHolder.setDuration(200L);
            arrayList.add(ofPropertyValuesHolder);
        }
        ObjectAnimator ofPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(secQSCustomizerAnimator.mView.findViewById(R.id.qs_customizer_top_summary), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 0.0f));
        ofPropertyValuesHolder2.setDuration(200L);
        arrayList.add(ofPropertyValuesHolder2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerAnimator$startClosingAnim$2$1$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                secQSCustomizerController$$ExternalSyntheticLambda2.run();
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
        });
        animatorSet.start();
    }

    public final void updateFont(int i, int i2) {
        FontSizeUtils.updateFontSize((TextView) ((SecQSCustomizerBase) this.mView).findViewById(i), i2, 0.8f, 1.3f);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.customize.SecQSCustomizerController$7 */
    public final class C21477 implements Animator.AnimatorListener {
        public final /* synthetic */ View val$view;

        public C21477(View view) {
            this.val$view = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
            SecQSCustomizerBase secQSCustomizerBase = (SecQSCustomizerBase) secQSCustomizerController.mView;
            SecCustomizeTileView secCustomizeTileView = secQSCustomizerController.mLongClickedView;
            int i = secQSCustomizerController.mWhereAmI;
            float f = secQSCustomizerController.mPointX;
            float f2 = secQSCustomizerController.mPointY;
            secQSCustomizerBase.getClass();
            SecQSCustomizerBase.C21381 c21381 = new View.DragShadowBuilder(secQSCustomizerBase, secCustomizeTileView, f, f2) { // from class: com.android.systemui.qs.customize.SecQSCustomizerBase.1
                public final /* synthetic */ float val$pointX;
                public final /* synthetic */ float val$pointY;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C21381(SecQSCustomizerBase secQSCustomizerBase2, View secCustomizeTileView2, float f3, float f22) {
                    super(secCustomizeTileView2);
                    this.val$pointX = f3;
                    this.val$pointY = f22;
                }

                @Override // android.view.View.DragShadowBuilder
                public final void onProvideShadowMetrics(Point point, Point point2) {
                    int width = getView().getWidth();
                    int height = getView().getHeight();
                    point.set(width, height);
                    point2.set(((int) this.val$pointX) + ((int) (width * 0.125f)), ((int) this.val$pointY) + ((int) (height * 0.25f)));
                }
            };
            boolean z = false;
            try {
                z = this.val$view.startDragAndDrop(new ClipData(new ClipDescription("", new String[0]), new ClipData.Item(new Intent())), c21381, null, 1048832);
            } catch (IllegalStateException unused) {
            }
            if (!z) {
                ((SecQSCustomizerBase) SecQSCustomizerController.this.mView).requestLayout();
            }
            this.val$view.setAlpha(0.0f);
            this.val$view.postDelayed(new SecQSCustomizerController$$ExternalSyntheticLambda2(this, 3), 100L);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            SecQSCustomizerController.this.mLongClickedView.mLabelContainer.setVisibility(8);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
