package com.android.systemui.qs.customize.viewcontroller;

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
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.HapticFeedbackConstants;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomTileInfo;
import com.android.systemui.qs.customize.CustomizerTileLayout;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.CustomizerTileViewPager.AnonymousClass4;
import com.android.systemui.qs.customize.MessageObjectAnim;
import com.android.systemui.qs.customize.SecCustomizeTileView;
import com.android.systemui.qs.customize.SecQSCustomizerAnimator;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.QSFullTileCustomizer;
import com.android.systemui.qs.customize.view.QSTileCustomizerBase;
import com.android.systemui.qs.customize.view.QSTopTileCustomizer;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.builders.ListBuilder;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSTileCustomizerController extends ViewControllerBase {
    public final LinearLayout mActiveParent;
    public final ScrollView mActiveScrollView;
    public final CustomizerTileViewPager mActiveTileLayout;
    public final AudioManager mAudioManager;
    public final LinearLayout mAvailableArea;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public final QSTileCustomizerController$$ExternalSyntheticLambda0 mClickListener;
    public final Context mContext;
    public final View mDoneButton;
    public Runnable mDoneCallBack;
    public final AnonymousClass1 mDoneOnClickListener;
    public final AnonymousClass7 mDragListener;
    public final SecQSSettingEditResources mEditResources;
    public final AnonymousClass8 mHandler;
    public boolean mIsDroppedOnView;
    public boolean mIsReadyToClick;
    public final boolean mIsTopEdit;
    public final AnonymousClass5 mLongClickListener;
    public SecCustomizeTileView mLongClickedView;
    public CustomTileInfo mLongClickedViewInfo;
    public float mPointX;
    public float mPointY;
    public final View mResetButton;
    public SystemUIDialog mResetDialog;
    public final AnonymousClass2 mResetOnClickListener;
    public final SecQSCustomizerTileAdapter mTileAdapter;
    public final int mTopMinMaxNum;
    public int mWhereAmI;

    /* renamed from: $r8$lambda$7xG1Eqhj4F-3t-MC9dkLkGgZKGg, reason: not valid java name */
    public static void m2068$r8$lambda$7xG1Eqhj4F3tMC9dkLkGgZKGg(final QSTileCustomizerController qSTileCustomizerController, View view) {
        if (!qSTileCustomizerController.mIsReadyToClick || ((QSTileCustomizerBase) qSTileCustomizerController.mView).mIsDragging) {
            return;
        }
        qSTileCustomizerController.mIsReadyToClick = false;
        View view2 = view.getId() == SecQSSettingEditResources.REMOVE_ICON_ID ? (View) view.getTag() : view;
        int id = ((View) view2.getParent().getParent()).getId();
        final CustomTileInfo customTileInfo = (CustomTileInfo) view2.getTag();
        AnonymousClass8 anonymousClass8 = qSTileCustomizerController.mHandler;
        if (id == R.id.qs_customizer_active_pager) {
            qSTileCustomizerController.animateArea(customTileInfo, 1000, 19998);
            final int i = 0;
            anonymousClass8.postDelayed(new Runnable(qSTileCustomizerController) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda1
                public final /* synthetic */ QSTileCustomizerController f$0;

                {
                    this.f$0 = qSTileCustomizerController;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            QSTileCustomizerController qSTileCustomizerController2 = this.f$0;
                            CustomTileInfo customTileInfo2 = customTileInfo;
                            qSTileCustomizerController2.getClass();
                            MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
                            messageObjectAnim.animationType = 210;
                            messageObjectAnim.longClickedTileInfo = customTileInfo2;
                            qSTileCustomizerController2.removeAreaAnimationMessage();
                            QSTileCustomizerController.AnonymousClass8 anonymousClass82 = qSTileCustomizerController2.mHandler;
                            if (anonymousClass82.hasMessages(101)) {
                                anonymousClass82.removeMessages(101);
                            }
                            anonymousClass82.sendMessage(anonymousClass82.obtainMessage(101, messageObjectAnim));
                            break;
                        default:
                            this.f$0.animationDrop(customTileInfo);
                            break;
                    }
                }
            }, 100L);
            view.announceForAccessibility(String.format(qSTileCustomizerController.getContext().getString(R.string.qs_custom_action_removed_done), ((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label));
        } else if (id == R.id.qs_customizer_available_pager) {
            qSTileCustomizerController.animateArea(customTileInfo, 2000, 9999);
            final int i2 = 1;
            anonymousClass8.postDelayed(new Runnable(qSTileCustomizerController) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda1
                public final /* synthetic */ QSTileCustomizerController f$0;

                {
                    this.f$0 = qSTileCustomizerController;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            QSTileCustomizerController qSTileCustomizerController2 = this.f$0;
                            CustomTileInfo customTileInfo2 = customTileInfo;
                            qSTileCustomizerController2.getClass();
                            MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
                            messageObjectAnim.animationType = 210;
                            messageObjectAnim.longClickedTileInfo = customTileInfo2;
                            qSTileCustomizerController2.removeAreaAnimationMessage();
                            QSTileCustomizerController.AnonymousClass8 anonymousClass82 = qSTileCustomizerController2.mHandler;
                            if (anonymousClass82.hasMessages(101)) {
                                anonymousClass82.removeMessages(101);
                            }
                            anonymousClass82.sendMessage(anonymousClass82.obtainMessage(101, messageObjectAnim));
                            break;
                        default:
                            this.f$0.animationDrop(customTileInfo);
                            break;
                    }
                }
            }, 100L);
            view.announceForAccessibility(String.format(qSTileCustomizerController.getContext().getString(R.string.qs_custom_action_added_done), ((CustomTileInfo) customTileInfo.customTileView.getTag()).state.label));
        }
        anonymousClass8.postDelayed(new QSTileCustomizerController$$ExternalSyntheticLambda3(qSTileCustomizerController, 0), 500L);
    }

    /* renamed from: -$$Nest$mdone, reason: not valid java name */
    public static void m2069$$Nest$mdone(QSTileCustomizerController qSTileCustomizerController) {
        if (((QSTileCustomizerBase) qSTileCustomizerController.mView).isShown()) {
            List spec = qSTileCustomizerController.mActiveTileLayout.getSpec();
            List quickPanelItems = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).getQuickPanelItems();
            int size = quickPanelItems != null ? quickPanelItems.size() : 0;
            if (qSTileCustomizerController.mIsTopEdit) {
                int size2 = ((ArrayList) spec).size();
                int i = qSTileCustomizerController.mTopMinMaxNum;
                if (size2 != i && size == 0) {
                    Toast.makeText(((QSTileCustomizerBase) qSTileCustomizerController.mView).getContext(), ((QSTileCustomizerBase) qSTileCustomizerController.mView).getContext().getResources().getQuantityString(R.plurals.sec_qs_add_minimum, i, Integer.valueOf(i)), 0).show();
                    return;
                }
            }
            qSTileCustomizerController.doneCallback = qSTileCustomizerController.mDoneCallBack;
            qSTileCustomizerController.save$1();
            qSTileCustomizerController.close();
        }
    }

    /* renamed from: -$$Nest$mmoveToArea, reason: not valid java name */
    public static void m2070$$Nest$mmoveToArea(QSTileCustomizerController qSTileCustomizerController, MessageObjectAnim messageObjectAnim) {
        qSTileCustomizerController.getClass();
        CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
        int i = messageObjectAnim.animationType;
        CustomizerTileViewPager customizerTileViewPager = qSTileCustomizerController.mAvailableTileLayout;
        CustomizerTileViewPager customizerTileViewPager2 = qSTileCustomizerController.mActiveTileLayout;
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = qSTileCustomizerController.mTileAdapter;
        if (i == 1000) {
            customTileInfo.customizeTileContentDes = qSTileCustomizerController.mContext.getString(R.string.qs_edit_setting_available_area_tapped) + " " + ((Object) customTileInfo.state.label) + ", ";
            customTileInfo.isActive = false;
            customizerTileViewPager2.removeTile(customTileInfo);
            customizerTileViewPager.addTile(customTileInfo, messageObjectAnim.touchedPos);
            secQSCustomizerTileAdapter.updateRemovedTileList(customTileInfo.spec, false);
        } else {
            boolean z = qSTileCustomizerController.mIsTopEdit;
            if (z) {
                int size = ((customizerTileViewPager2.mPages.size() - 1) * customizerTileViewPager2.mColumns * customizerTileViewPager2.mRows) + (customizerTileViewPager2.mAdapter.getCount() - 1 < customizerTileViewPager2.mPages.size() ? ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(customizerTileViewPager2.mAdapter.getCount() - 1)).mCustomTilesInfo.size() : 0);
                int i2 = qSTileCustomizerController.mTopMinMaxNum;
                if (size >= i2) {
                    QSTileCustomizerBase qSTileCustomizerBase = (QSTileCustomizerBase) qSTileCustomizerController.mView;
                    qSTileCustomizerBase.mMinNum = i2;
                    Resources resources = qSTileCustomizerBase.mContext.getResources();
                    int i3 = qSTileCustomizerBase.mMinNum;
                    String quantityString = resources.getQuantityString(R.plurals.sec_qs_unable_add_maximum, i3, Integer.valueOf(i3));
                    if (qSTileCustomizerBase.mToast == null) {
                        qSTileCustomizerBase.mToast = Toast.makeText(qSTileCustomizerBase.mContext, "", 0);
                    }
                    qSTileCustomizerBase.mToast.setText(quantityString);
                    qSTileCustomizerBase.mToast.show();
                    return;
                }
            }
            if (!z && messageObjectAnim.touchedPos >= 9999) {
                qSTileCustomizerController.mActiveScrollView.fullScroll(130);
            }
            customTileInfo.customizeTileContentDes = qSTileCustomizerController.mContext.getString(R.string.qs_edit_setting_active_area_tapped) + " " + ((Object) customTileInfo.state.label) + ", ";
            customTileInfo.isActive = true;
            customizerTileViewPager2.addTile(customTileInfo, messageObjectAnim.touchedPos);
            customizerTileViewPager.removeTile(customTileInfo);
            secQSCustomizerTileAdapter.updateRemovedTileList(customTileInfo.spec, true);
        }
        qSTileCustomizerController.mWhereAmI = i == 1000 ? PluginEdgeLightingPlus.VERSION : 5000;
    }

    /* renamed from: -$$Nest$mshowResetDialog, reason: not valid java name */
    public static void m2071$$Nest$mshowResetDialog(QSTileCustomizerController qSTileCustomizerController) {
        if (qSTileCustomizerController.mResetDialog == null) {
            SystemUIDialog systemUIDialog = new SystemUIDialog(((QSTileCustomizerBase) qSTileCustomizerController.mView).getContext(), R.style.Theme_SystemUI_Dialog_Alert);
            qSTileCustomizerController.mResetDialog = systemUIDialog;
            systemUIDialog.setMessage(R.string.sec_qs_edit_panel_reset_dialog_message);
            qSTileCustomizerController.mResetDialog.setPositiveButton(R.string.quick_settings_reset, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("QSTileCustomizerController", "RESET");
                    QSTileCustomizerController qSTileCustomizerController2 = QSTileCustomizerController.this;
                    StringBuilder sb = new StringBuilder("reset =  ");
                    SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = qSTileCustomizerController2.mTileAdapter;
                    sb.append(secQSCustomizerTileAdapter.mCurrentSpecs);
                    Log.d("SecQSCustomizerTileAdapter", sb.toString());
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    CustomizerTileViewPager customizerTileViewPager = qSTileCustomizerController2.mActiveTileLayout;
                    List spec = customizerTileViewPager.getSpec();
                    boolean z = secQSCustomizerTileAdapter.mIsTopEdit;
                    QSTileHost qSTileHost = secQSCustomizerTileAdapter.mHost;
                    String qQSDefaultTileList = z ? secQSCustomizerTileAdapter.mQQSHost.getQQSDefaultTileList() : qSTileHost.getDefaultTileList();
                    Log.d("SecQSCustomizerTileAdapter", "cscTileList: " + qQSDefaultTileList);
                    arrayList.addAll(customizerTileViewPager.getTilesInfo());
                    CustomizerTileViewPager customizerTileViewPager2 = qSTileCustomizerController2.mAvailableTileLayout;
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
                                CustomTileInfo customTileInfo = (CustomTileInfo) it.next();
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
                            ((CustomTileInfo) it2.next()).isActive = false;
                        }
                        customizerTileViewPager2.addTiles(arrayList);
                        Settings.Secure.putStringForUser(qSTileHost.mContext.getContentResolver(), "sysui_removed_qs_tiles", "", ((UserTrackerImpl) qSTileHost.mUserTracker).getUserId());
                    }
                    if (!((QSTileCustomizerBase) ((ViewController) QSTileCustomizerController.this).mView).isShown()) {
                        QSTileCustomizerController.this.save$1();
                    }
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), QSTileCustomizerController.this.mIsTopEdit ? SystemUIAnalytics.EID_EDIT_TOP_RESET : SystemUIAnalytics.EID_EDIT_RESET);
                }
            });
            qSTileCustomizerController.mResetDialog.setNegativeButton(R.string.no, null);
            qSTileCustomizerController.mResetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.4
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    QSTileCustomizerController.this.mResetDialog = null;
                }
            });
            qSTileCustomizerController.mResetDialog.show();
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$2] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$5] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$7] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$8] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda0] */
    public QSTileCustomizerController(Context context, SecQSSettingEditResources secQSSettingEditResources) {
        super(secQSSettingEditResources.isCurrentTopEdit ? new QSTopTileCustomizer(context) : new QSFullTileCustomizer(context));
        View requireViewById;
        this.mIsReadyToClick = true;
        boolean z = false;
        this.mWhereAmI = 0;
        this.mIsDroppedOnView = false;
        this.mDoneOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTileCustomizerController.m2069$$Nest$mdone(QSTileCustomizerController.this);
            }
        };
        this.mResetOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog = QSTileCustomizerController.this.mResetDialog;
                if (systemUIDialog == null || !systemUIDialog.isShowing()) {
                    QSTileCustomizerController.m2071$$Nest$mshowResetDialog(QSTileCustomizerController.this);
                }
            }
        };
        this.mLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.5
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                if (((QSTileCustomizerBase) ((ViewController) QSTileCustomizerController.this).mView).mIsDragging) {
                    Log.d("QSTileCustomizerController", "onLongClick mView.isDragging() skip : dulicated long click");
                    return true;
                }
                view.setBackgroundResource(0);
                if (view instanceof SecCustomizeTileView) {
                    QSTileCustomizerController.this.mLongClickedView = (SecCustomizeTileView) view;
                }
                QSTileCustomizerController qSTileCustomizerController = QSTileCustomizerController.this;
                if (qSTileCustomizerController.mLongClickedView == null) {
                    return false;
                }
                qSTileCustomizerController.mLongClickedViewInfo = (CustomTileInfo) view.getTag();
                QSTileCustomizerController qSTileCustomizerController2 = QSTileCustomizerController.this;
                SecCustomizeTileView secCustomizeTileView = qSTileCustomizerController2.mLongClickedView;
                qSTileCustomizerController2.mPointX = secCustomizeTileView.mPointX;
                qSTileCustomizerController2.mPointY = secCustomizeTileView.mPointY;
                View view2 = (View) view.getParent().getParent();
                QSTileCustomizerController.this.mWhereAmI = view2.getId() == R.id.qs_customizer_active_pager ? 5000 : PluginEdgeLightingPlus.VERSION;
                QSTileCustomizerController qSTileCustomizerController3 = QSTileCustomizerController.this;
                CustomizerTileViewPager customizerTileViewPager = qSTileCustomizerController3.mWhereAmI == 5000 ? qSTileCustomizerController3.mActiveTileLayout : qSTileCustomizerController3.mAvailableTileLayout;
                CustomTileInfo customTileInfo = qSTileCustomizerController3.mLongClickedViewInfo;
                if (customizerTileViewPager.mPages.size() != 0) {
                    ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(customizerTileViewPager.getCurrentItem())).showRemoveIcon(customTileInfo, false);
                }
                QSTileCustomizerController qSTileCustomizerController4 = QSTileCustomizerController.this;
                CustomTileInfo customTileInfo2 = qSTileCustomizerController4.mLongClickedViewInfo;
                int i = qSTileCustomizerController4.mWhereAmI;
                qSTileCustomizerController4.mLongClickedViewInfo = customTileInfo2;
                qSTileCustomizerController4.mWhereAmI = i;
                AudioManager audioManager = qSTileCustomizerController4.mAudioManager;
                if (audioManager != null) {
                    audioManager.playSoundEffect(106);
                }
                QSTileCustomizerController.this.mLongClickedView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                QSTileCustomizerController qSTileCustomizerController5 = QSTileCustomizerController.this;
                CustomTileInfo customTileInfo3 = qSTileCustomizerController5.mLongClickedViewInfo;
                MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
                messageObjectAnim.animationType = 200;
                messageObjectAnim.longClickedTileInfo = customTileInfo3;
                AnonymousClass8 anonymousClass8 = qSTileCustomizerController5.mHandler;
                if (anonymousClass8.hasMessages(100)) {
                    anonymousClass8.removeMessages(100);
                }
                anonymousClass8.sendMessage(anonymousClass8.obtainMessage(100, messageObjectAnim));
                QSTileCustomizerController qSTileCustomizerController6 = QSTileCustomizerController.this;
                qSTileCustomizerController6.getClass();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(80L);
                ofFloat.addListener(qSTileCustomizerController6.new AnonymousClass6(view));
                ofFloat.start();
                view.announceForAccessibility(String.format(QSTileCustomizerController.this.getContext().getString(R.string.qs_custom_action_long_pressed), ((CustomTileInfo) QSTileCustomizerController.this.mLongClickedViewInfo.customTileView.getTag()).state.label));
                return true;
            }
        };
        this.mDragListener = new View.OnDragListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.7
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:51:0x0192, code lost:
            
                return true;
             */
            @Override // android.view.View.OnDragListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onDrag(android.view.View r10, android.view.DragEvent r11) {
                /*
                    Method dump skipped, instructions count: 420
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.AnonymousClass7.onDrag(android.view.View, android.view.DragEvent):boolean");
            }
        };
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.8
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i;
                RecyclerView$$ExternalSyntheticOutline0.m(message.what, "QSTileCustomizerController", new StringBuilder("handleMessage() msg.what="));
                QSTileCustomizerController qSTileCustomizerController = QSTileCustomizerController.this;
                CustomizerTileViewPager customizerTileViewPager = qSTileCustomizerController.mWhereAmI == 5000 ? qSTileCustomizerController.mActiveTileLayout : qSTileCustomizerController.mAvailableTileLayout;
                switch (message.what) {
                    case 100:
                    case 101:
                    case 102:
                        MessageObjectAnim messageObjectAnim = (MessageObjectAnim) message.obj;
                        if (customizerTileViewPager.mPages.size() != 0) {
                            final int currentItem = customizerTileViewPager.getCurrentItem();
                            final CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
                            final int i2 = messageObjectAnim.touchedPos;
                            if (customTileInfo != null) {
                                StringBuilder sb = new StringBuilder("handleAnimate addInfo.spce");
                                sb.append(customTileInfo.spec);
                                sb.append("animation type = ");
                                RecyclerView$$ExternalSyntheticOutline0.m(messageObjectAnim.animationType, "CSTMPagedTileLayout", sb);
                                int i3 = messageObjectAnim.animationType;
                                if (i3 != 202) {
                                    if (i3 != 201) {
                                        if (i3 != 203 && i3 != 204) {
                                            if (i3 != 200) {
                                                if (i3 != 211) {
                                                    if (i3 == 210) {
                                                        Iterator it = customizerTileViewPager.mPages.iterator();
                                                        int i4 = -1;
                                                        while (true) {
                                                            if (it.hasNext()) {
                                                                i4++;
                                                                if (((CustomizerTileLayout) it.next()).indexOf(customTileInfo) >= 0) {
                                                                }
                                                            } else {
                                                                i4 = -1;
                                                            }
                                                        }
                                                        Log.d("CSTMPagedTileLayout", "handleAnimate dropTile: " + customTileInfo + ", pageIndex=" + i4);
                                                        if (i4 != -1) {
                                                            ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i4)).dropTile(customTileInfo);
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
                                                        SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) customizerTilePage.mCustomTilesInfo.get(indexOf)).customTileView;
                                                        if (CustomizerTileLayout.DEBUG) {
                                                            ListPopupWindow$$ExternalSyntheticOutline0.m(indexOf, "selectTileByAccessibility position = ", "CustomizerTileLayout");
                                                        }
                                                        secCustomizeTileView.mLabel.setAlpha(0.0f);
                                                        break;
                                                    }
                                                }
                                            } else {
                                                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).selectTile(customTileInfo, false);
                                                break;
                                            }
                                        } else if (customizerTileViewPager.mPages.size() != 0) {
                                            int i5 = messageObjectAnim.animationType;
                                            if (i5 == 204) {
                                                CustomizerTileViewPager.AnonymousClass1 anonymousClass1 = customizerTileViewPager.mAdapter;
                                                if (currentItem >= (anonymousClass1 != null ? anonymousClass1.getCount() : 0) - 1) {
                                                }
                                            } else if (currentItem <= 0) {
                                            }
                                            final int i6 = i5 == 204 ? 1 : -1;
                                            int columnCount = i5 == 204 ? 0 : (customizerTileViewPager.getColumnCount() * customizerTileViewPager.mRows) - 1;
                                            final int columnCount2 = i5 == 204 ? (customizerTileViewPager.getColumnCount() * customizerTileViewPager.mRows) - 1 : 0;
                                            int columnCount3 = (customizerTileViewPager.getColumnCount() * customizerTileViewPager.mRows) - 1;
                                            final int i7 = currentItem + i6;
                                            final CustomTileInfo info = ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).getInfo(columnCount);
                                            if (info != null) {
                                                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).removeTile(info, false);
                                                final CustomTileInfo customTileInfo2 = messageObjectAnim.longClickedTileInfo;
                                                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).addTile(customTileInfo2, columnCount3, false);
                                                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).selectTile(customTileInfo2, true);
                                                final CustomizerTileViewPager customizerTileViewPager2 = customizerTileViewPager;
                                                customizerTileViewPager.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager$$ExternalSyntheticLambda0
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        CustomizerTileViewPager customizerTileViewPager3 = CustomizerTileViewPager.this;
                                                        int i8 = currentItem;
                                                        int i9 = i6;
                                                        int i10 = i7;
                                                        CustomTileInfo customTileInfo3 = customTileInfo2;
                                                        CustomTileInfo customTileInfo4 = info;
                                                        int i11 = columnCount2;
                                                        int i12 = CustomizerTileViewPager.$r8$clinit;
                                                        customizerTileViewPager3.getClass();
                                                        Log.d("CSTMPagedTileLayout", "cur " + i8 + "pageOffset" + i9);
                                                        if (i8 >= customizerTileViewPager3.mPages.size() || customizerTileViewPager3.mPages.get(i8) == null) {
                                                            return;
                                                        }
                                                        customizerTileViewPager3.setCurrentItem(i10, true);
                                                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager3.mPages.get(i8)).removeTile(customTileInfo3, false);
                                                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager3.mPages.get(i8)).addTile(customTileInfo4, i11, false);
                                                        FrameLayout frameLayout = ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager3.mPages.get(i8)).mCircle;
                                                        if (frameLayout != null) {
                                                            frameLayout.setAlpha(0.0f);
                                                        }
                                                    }
                                                }, 210L);
                                                break;
                                            }
                                        }
                                    } else {
                                        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem)).dropTile(customTileInfo);
                                        break;
                                    }
                                } else {
                                    final CustomizerTileViewPager.CustomizerTilePage customizerTilePage2 = (CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(currentItem);
                                    int indexOf2 = customizerTilePage2.indexOf(customTileInfo);
                                    if (indexOf2 >= 0) {
                                        int min = Math.min(customizerTilePage2.mColumns * customizerTilePage2.mMaxRows, customizerTilePage2.mCustomTilesInfo.size());
                                        int i8 = min - 1;
                                        if (i2 > i8) {
                                            i2 = i8;
                                        }
                                        if (CustomizerTileLayout.DEBUG) {
                                            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(indexOf2, i2, "moveTile from = ", " to  = ", "total = ");
                                            m.append(min);
                                            m.append("fromtileInfo = ");
                                            ExifInterface$$ExternalSyntheticOutline0.m(m, customTileInfo.spec, "CustomizerTileLayout");
                                        }
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        if (indexOf2 < i2) {
                                            int i9 = indexOf2;
                                            while (i9 < i2) {
                                                int i10 = i9 + 1;
                                                SecCustomizeTileView secCustomizeTileView2 = ((CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(i10)).customTileView;
                                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "x", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i10)).getLeft(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i9)).getLeft()));
                                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "y", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i10)).getTop(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(i9)).getTop()));
                                                indexOf2 = indexOf2;
                                                i9 = i10;
                                            }
                                            i = indexOf2;
                                        } else {
                                            i = indexOf2;
                                            while (indexOf2 > i2) {
                                                SecCustomizeTileView secCustomizeTileView3 = ((CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(indexOf2 - 1)).customTileView;
                                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView3, "x", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(r12)).getLeft(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(indexOf2)).getLeft()));
                                                animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView3, "y", ((FrameLayout) customizerTilePage2.mBoundaryBox.get(r12)).getTop(), ((FrameLayout) customizerTilePage2.mBoundaryBox.get(indexOf2)).getTop()));
                                                indexOf2--;
                                            }
                                        }
                                        animatorSet.setDuration(200L);
                                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.3
                                            public final /* synthetic */ int val$emptyPos;
                                            public final /* synthetic */ CustomTileInfo val$fromtileInfo;

                                            public AnonymousClass3(final int i22, final CustomTileInfo customTileInfo3) {
                                                r2 = i22;
                                                r3 = customTileInfo3;
                                            }

                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationStart(Animator animator) {
                                                if (CustomizerTileLayout.DEBUG) {
                                                    Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                                                }
                                                CustomizerTileLayout.this.setCircleTranslation(r2, r3.isActive);
                                            }
                                        });
                                        animatorSet.start();
                                        int i11 = i;
                                        CustomTileInfo customTileInfo3 = (CustomTileInfo) customizerTilePage2.mCustomTilesInfo.get(i11);
                                        customizerTilePage2.mCustomTilesInfo.remove(i11);
                                        customizerTilePage2.mCustomTilesInfo.add(i22, customTileInfo3);
                                        break;
                                    }
                                }
                            } else {
                                Log.e("CSTMPagedTileLayout", "TileInfo is null");
                                break;
                            }
                        }
                        break;
                    case 103:
                        QSTileCustomizerController.m2070$$Nest$mmoveToArea(qSTileCustomizerController, (MessageObjectAnim) message.obj);
                        break;
                }
            }
        };
        this.mClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTileCustomizerController.m2068$r8$lambda$7xG1Eqhj4F3tMC9dkLkGgZKGg(QSTileCustomizerController.this, view);
            }
        };
        QSTileCustomizerBase qSTileCustomizerBase = (QSTileCustomizerBase) this.mView;
        qSTileCustomizerBase.mEditResources = secQSSettingEditResources;
        this.mIsTopEdit = secQSSettingEditResources.isCurrentTopEdit;
        this.mContext = context;
        this.mResetButton = qSTileCustomizerBase.findViewById(R.id.left_button);
        this.mDoneButton = ((QSTileCustomizerBase) this.mView).findViewById(R.id.right_button);
        SecQSCustomizerAnimator.Companion companion = SecQSCustomizerAnimator.Companion;
        T t = this.mView;
        companion.getClass();
        int displayHeight = (int) (DeviceState.getDisplayHeight(t.getContext()) * 0.0625d);
        ListBuilder activeTileContents = SecQSCustomizerAnimator.Companion.getActiveTileContents(t);
        ListBuilder availableTileContents = SecQSCustomizerAnimator.Companion.getAvailableTileContents(t);
        ListIterator listIterator = activeTileContents.listIterator(0);
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
            if (!itr.hasNext()) {
                break;
            }
            View view = (View) itr.next();
            if (view != null) {
                view.setAlpha(0.0f);
            }
        }
        ListIterator listIterator2 = availableTileContents.listIterator(0);
        while (true) {
            ListBuilder.Itr itr2 = (ListBuilder.Itr) listIterator2;
            if (!itr2.hasNext()) {
                break;
            }
            View view2 = (View) itr2.next();
            if (view2 != null) {
                view2.setTranslationY(displayHeight);
                view2.setAlpha(0.0f);
            }
        }
        View view3 = SecQSCustomizerAnimator.mainView;
        if (view3 != null && (requireViewById = view3.requireViewById(R.id.navigation_bar_view)) != null) {
            requireViewById.setAlpha(0.0f);
        }
        View findViewById = t.findViewById(R.id.qs_customize_top_summary_buttons);
        if (findViewById != null) {
            findViewById.setScaleY(0.95f);
            findViewById.setScaleX(0.95f);
        }
        this.mEditResources = secQSSettingEditResources;
        CustomizerTileViewPager customizerTileViewPager = (CustomizerTileViewPager) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customizer_active_pager);
        this.mActiveTileLayout = customizerTileViewPager;
        customizerTileViewPager.setIsTopEdit(this.mIsTopEdit);
        CustomizerTileViewPager customizerTileViewPager2 = (CustomizerTileViewPager) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customizer_available_pager);
        this.mAvailableTileLayout = customizerTileViewPager2;
        customizerTileViewPager2.setIsTopEdit(this.mIsTopEdit);
        this.mTopMinMaxNum = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getQsTileMinNum(((QSTileCustomizerBase) this.mView).getContext());
        this.mAudioManager = (AudioManager) getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        boolean z2 = this.mIsTopEdit;
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = z2 ? secQSSettingEditResources.tileTopAdapter : secQSSettingEditResources.tileFullAdapter;
        this.mTileAdapter = secQSCustomizerTileAdapter;
        if (!z2) {
            List list = secQSCustomizerTileAdapter.mAllTiles;
            int size = list == null ? -1 : list.size();
            QSTileCustomizerBase qSTileCustomizerBase2 = (QSTileCustomizerBase) this.mView;
            size = size <= 0 ? 70 : size;
            int panelColumns = qSTileCustomizerBase2.mEditResources.getPanelColumns();
            qSTileCustomizerBase2.mActiveColumns = panelColumns;
            qSTileCustomizerBase2.mActiveRows = (size % panelColumns) + (size / panelColumns) + 1;
        }
        customizerTileViewPager.mInitialPagenum = secQSCustomizerTileAdapter.mActiveCurrentPage;
        customizerTileViewPager2.mInitialPagenum = secQSCustomizerTileAdapter.mAvailableCurrentPage;
        this.mActiveScrollView = (ScrollView) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_active_page_scrollview);
        this.mAvailableArea = (LinearLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_available_area);
        this.mActiveParent = (LinearLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_active_page_parent);
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_area_corner_radius);
        this.mAvailableArea.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(14).setBackgroundCornerRadius(dimensionPixelSize, dimensionPixelSize, 0.0f, 0.0f).setRadius(200).build());
        if (this.mIsTopEdit) {
            this.mActiveParent.semSetBlurInfo(null);
        } else {
            this.mActiveParent.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(14).setBackgroundCornerRadius(dimensionPixelSize).build());
        }
        ((QSTileCustomizerBase) this.mView).updateResources();
        ArrayList arrayList = secQSCustomizerTileAdapter.mActiveTiles;
        if (arrayList != null) {
            customizerTileViewPager.addTiles(arrayList);
            Iterator it = secQSCustomizerTileAdapter.mActiveTiles.iterator();
            while (it.hasNext()) {
                ((CustomTileInfo) it.next()).longClickListener = this.mLongClickListener;
            }
        }
        SecQSSettingEditResources secQSSettingEditResources2 = this.mEditResources;
        if (this.mIsTopEdit) {
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSSettingEditResources2.tileTopAdapter;
            if (secQSCustomizerTileAdapter2 != null) {
                z = secQSCustomizerTileAdapter2.mIsLoadedAllTiles;
            }
        } else {
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter3 = secQSSettingEditResources2.tileFullAdapter;
            if (secQSCustomizerTileAdapter3 != null) {
                z = secQSCustomizerTileAdapter3.mIsLoadedAllTiles;
            }
        }
        if (z) {
            ArrayList arrayList2 = secQSCustomizerTileAdapter.mAvailableTiles;
            if (arrayList2 != null) {
                secQSCustomizerTileAdapter.mOnTileChangedCallback = null;
                this.mAvailableTileLayout.addTiles(arrayList2);
                Iterator it2 = secQSCustomizerTileAdapter.mAvailableTiles.iterator();
                while (it2.hasNext()) {
                    ((CustomTileInfo) it2.next()).longClickListener = this.mLongClickListener;
                }
            }
        } else {
            secQSCustomizerTileAdapter.mOnTileChangedCallback = new QSTileCustomizerController$$ExternalSyntheticLambda4(this);
        }
        this.message = 100;
    }

    public final void animateArea(CustomTileInfo customTileInfo, int i, int i2) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = i;
        messageObjectAnim.touchedPos = i2;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        AnonymousClass8 anonymousClass8 = this.mHandler;
        anonymousClass8.sendMessageDelayed(anonymousClass8.obtainMessage(103, messageObjectAnim), 100L);
    }

    public final void animationDrop(CustomTileInfo customTileInfo) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = 201;
        messageObjectAnim.longClickedTileInfo = customTileInfo;
        removeAreaAnimationMessage();
        AnonymousClass8 anonymousClass8 = this.mHandler;
        if (anonymousClass8.hasMessages(101)) {
            anonymousClass8.removeMessages(101);
        }
        anonymousClass8.sendMessageDelayed(anonymousClass8.obtainMessage(101, messageObjectAnim), 0L);
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void close() {
        if (this.isShown) {
            super.close();
            ((QSTileCustomizerBase) this.mView).getClass();
            Log.d("QSTileCustomizerBase", "close customizer");
            SecQSCustomizerAnimator.Companion companion = SecQSCustomizerAnimator.Companion;
            final Runnable runnable = this.doneCallback;
            T t = this.mView;
            companion.getClass();
            int displayHeight = (int) (DeviceState.getDisplayHeight(t.getContext()) * 0.0625d);
            ListBuilder activeTileContents = SecQSCustomizerAnimator.Companion.getActiveTileContents(t);
            ListBuilder availableTileContents = SecQSCustomizerAnimator.Companion.getAvailableTileContents(t);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(activeTileContents);
            arrayList2.addAll(availableTileContents);
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder((View) it.next(), PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, displayHeight));
                ofPropertyValuesHolder.setDuration(200L);
                arrayList.add(ofPropertyValuesHolder);
            }
            ObjectAnimator ofPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(t.findViewById(R.id.qs_customize_top_summary_buttons), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 0.0f));
            ofPropertyValuesHolder2.setDuration(200L);
            arrayList.add(ofPropertyValuesHolder2);
            View view = SecQSCustomizerAnimator.mainView;
            if (view != null) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view.requireViewById(R.id.navigation_bar_view), (Property<View, Float>) View.ALPHA, 0.0f);
                ofFloat.setDuration(200L);
                arrayList.add(ofFloat);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            if (runnable != null) {
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerAnimator$Companion$playAnimatorSet$1$1$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        runnable.run();
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
            }
            animatorSet.start();
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ViewPropertyAnimator animate;
        ViewPropertyAnimator scaleY;
        ViewPropertyAnimator scaleX;
        ViewPropertyAnimator duration;
        View requireViewById;
        ViewPropertyAnimator animate2;
        ViewPropertyAnimator alpha;
        ViewPropertyAnimator duration2;
        ViewPropertyAnimator animate3;
        ViewPropertyAnimator translationY;
        ViewPropertyAnimator alpha2;
        ViewPropertyAnimator duration3;
        ViewPropertyAnimator animate4;
        ViewPropertyAnimator alpha3;
        ViewPropertyAnimator duration4;
        this.mResetButton.setOnClickListener(this.mResetOnClickListener);
        this.mDoneButton.setOnClickListener(this.mDoneOnClickListener);
        TextView textView = (TextView) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_edit_summary);
        textView.setSelected(true);
        textView.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.9
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
            }
        });
        ((TextView) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_edit_more_summary)).setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.10
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
            }
        });
        AnonymousClass7 anonymousClass7 = this.mDragListener;
        CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
        customizerTileViewPager.mDragListener = anonymousClass7;
        customizerTileViewPager.mClickListener = this.mClickListener;
        View findViewById = ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_edit_available_text);
        View findViewById2 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customize_top_summary_buttons);
        View findViewById3 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.scroll_top_area);
        View findViewById4 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.scroll_bottom_area);
        findViewById.setTag(9999);
        findViewById2.setTag(9999);
        findViewById3.setTag(9999);
        findViewById4.setTag(9999);
        findViewById.setOnDragListener(this.mDragListener);
        findViewById2.setOnDragListener(this.mDragListener);
        findViewById3.setOnDragListener(this.mDragListener);
        findViewById4.setOnDragListener(this.mDragListener);
        SecPageIndicator secPageIndicator = (SecPageIndicator) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_available_paged_indicator);
        View requireViewById2 = ((QSTileCustomizerBase) this.mView).requireViewById(R.id.customize_container);
        CustomizerTileViewPager customizerTileViewPager2 = this.mAvailableTileLayout;
        customizerTileViewPager2.mParentContainer = requireViewById2;
        customizerTileViewPager2.mPageIndicator = secPageIndicator;
        secPageIndicator.setLocation(customizerTileViewPager2.mPageIndicatorPosition);
        SecPageIndicator secPageIndicator2 = customizerTileViewPager2.mPageIndicator;
        secPageIndicator2.mQsExpansion = 1.0f;
        secPageIndicator2.mCallback = customizerTileViewPager2.new AnonymousClass4();
        customizerTileViewPager2.mDragListener = this.mDragListener;
        customizerTileViewPager2.mClickListener = this.mClickListener;
        int color = getContext().getColor(R.color.qs_edit_available_page_indicator_tint_color_selected);
        int color2 = getContext().getColor(R.color.qs_edit_available_page_indicator_tint_color_unselected);
        SecPageIndicator secPageIndicator3 = customizerTileViewPager2.mPageIndicator;
        if (secPageIndicator3 != null) {
            secPageIndicator3.mSelectedColor = color;
            secPageIndicator3.mUnselectedColor = color2;
        }
        SecQSCustomizerAnimator.Companion companion = SecQSCustomizerAnimator.Companion;
        QSTileCustomizerBase qSTileCustomizerBase = (QSTileCustomizerBase) this.mView;
        companion.getClass();
        ListBuilder activeTileContents = SecQSCustomizerAnimator.Companion.getActiveTileContents(qSTileCustomizerBase);
        ListBuilder availableTileContents = SecQSCustomizerAnimator.Companion.getAvailableTileContents(qSTileCustomizerBase);
        ListIterator listIterator = activeTileContents.listIterator(0);
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
            if (!itr.hasNext()) {
                break;
            }
            View view = (View) itr.next();
            if (view != null && (animate4 = view.animate()) != null && (alpha3 = animate4.alpha(1.0f)) != null && (duration4 = alpha3.setDuration(100L)) != null) {
                duration4.start();
            }
        }
        ListIterator listIterator2 = availableTileContents.listIterator(0);
        while (true) {
            ListBuilder.Itr itr2 = (ListBuilder.Itr) listIterator2;
            if (!itr2.hasNext()) {
                break;
            }
            View view2 = (View) itr2.next();
            if (view2 != null && (animate3 = view2.animate()) != null && (translationY = animate3.translationY(0.0f)) != null && (alpha2 = translationY.alpha(1.0f)) != null && (duration3 = alpha2.setDuration(200L)) != null) {
                duration3.start();
            }
        }
        View view3 = SecQSCustomizerAnimator.mainView;
        if (view3 != null && (requireViewById = view3.requireViewById(R.id.navigation_bar_view)) != null && (animate2 = requireViewById.animate()) != null && (alpha = animate2.alpha(1.0f)) != null && (duration2 = alpha.setDuration(200L)) != null) {
            duration2.start();
        }
        View findViewById5 = qSTileCustomizerBase.findViewById(R.id.qs_customize_top_summary_buttons);
        if (findViewById5 == null || (animate = findViewById5.animate()) == null || (scaleY = animate.scaleY(1.0f)) == null || (scaleX = scaleY.scaleX(1.0f)) == null || (duration = scaleX.setDuration(200L)) == null) {
            return;
        }
        duration.start();
    }

    public final void removeAreaAnimationMessage() {
        AnonymousClass8 anonymousClass8 = this.mHandler;
        if (anonymousClass8.hasMessages(103)) {
            anonymousClass8.removeMessages(103);
        }
    }

    public final void save$1() {
        String str;
        String str2;
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = this.mTileAdapter;
        if (secQSCustomizerTileAdapter.mTileQueryHelper.mFinished) {
            Log.d("SecQSCustomizerTileAdapter", "mCurrentSpecs =  " + secQSCustomizerTileAdapter.mCurrentSpecs);
            CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
            List spec = customizerTileViewPager.getSpec();
            boolean equals = ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).equals(spec);
            boolean z = secQSCustomizerTileAdapter.mIsTopEdit;
            String str3 = SystemUIAnalytics.EID_EDIT_DONE;
            if (equals) {
                Log.d("SecQSCustomizerTileAdapter", "save none : same list");
                String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
                if (z) {
                    str3 = SystemUIAnalytics.EID_EDIT_TOP_DONE;
                }
                SystemUIAnalytics.sendRunestoneEventCDLog(currentScreenID, str3, "isChanged", "false", SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
                return;
            }
            String currentScreenID2 = SystemUIAnalytics.getCurrentScreenID();
            if (z) {
                str3 = SystemUIAnalytics.EID_EDIT_TOP_DONE;
            }
            SystemUIAnalytics.sendRunestoneEventCDLog(currentScreenID2, str3, "isChanged", "true", SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            if (z) {
                Prefs.putBoolean(secQSCustomizerTileAdapter.mContext, "QQsHasEditedQuickTileList", true);
                secQSCustomizerTileAdapter.mQQSHost.changeTiles(spec);
            } else {
                Prefs.putBoolean(secQSCustomizerTileAdapter.mContext, "QsHasEditedQuickTileList", true);
                secQSCustomizerTileAdapter.mHost.changeTilesByUser(secQSCustomizerTileAdapter.mCurrentSpecs, spec);
            }
            secQSCustomizerTileAdapter.mDefaultActiveTiles = customizerTileViewPager.getTilesInfo();
            ArrayList tilesInfo = this.mAvailableTileLayout.getTilesInfo();
            secQSCustomizerTileAdapter.mActiveTiles = secQSCustomizerTileAdapter.mDefaultActiveTiles;
            secQSCustomizerTileAdapter.mAvailableTiles = tilesInfo;
            secQSCustomizerTileAdapter.mActiveCurrentPage = 0;
            secQSCustomizerTileAdapter.mAvailableCurrentPage = 0;
            ArrayList arrayList = (ArrayList) spec;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str4 = (String) it.next();
                if (!((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).contains(str4)) {
                    if (str4.startsWith("custom(")) {
                        str2 = secQSCustomizerTileAdapter.mHost.getCustomTileNameFromSpec(str4);
                        if (str2 == null) {
                            str2 = CustomTile.getComponentFromSpec(str4).toShortString();
                        }
                    } else {
                        str2 = str4;
                    }
                    String valueOf = String.valueOf(arrayList.indexOf(str4) + 1);
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), z ? SystemUIAnalytics.EID_EDIT_TOP_MOVE_TO_ACTIVE : SystemUIAnalytics.EID_EDIT_MOVE_TO_ACTIVE, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_NAME, str2, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, valueOf);
                    MWBixbyController$$ExternalSyntheticOutline0.m("save add : ", str2, " ", valueOf, "SecQSCustomizerTileAdapter");
                }
            }
            Iterator it2 = ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).iterator();
            while (it2.hasNext()) {
                String str5 = (String) it2.next();
                if (str5.startsWith("custom(")) {
                    str = secQSCustomizerTileAdapter.mHost.getCustomTileNameFromSpec(str5);
                    if (str == null) {
                        str = CustomTile.getComponentFromSpec(str5).toShortString();
                    }
                } else {
                    str = str5;
                }
                if (!arrayList.contains(str5)) {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), z ? SystemUIAnalytics.EID_EDIT_TOP_MOVE_TO_AVAILABLE : SystemUIAnalytics.EID_EDIT_MOVE_TO_AVAILABLE, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_NAME, str);
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("save remove : "), str, "SecQSCustomizerTileAdapter");
                }
            }
            secQSCustomizerTileAdapter.mCurrentSpecs = spec;
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        if (this.isShown) {
            return;
        }
        super.show(runnable);
        ((QSTileCustomizerBase) this.mView).getClass();
        Log.d("QSTileCustomizerBase", "show customizer");
        this.doneCallback = null;
        this.mDoneCallBack = runnable;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$6, reason: invalid class name */
    public final class AnonymousClass6 implements Animator.AnimatorListener {
        public final /* synthetic */ View val$view;

        public AnonymousClass6(View view) {
            this.val$view = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            QSTileCustomizerBase qSTileCustomizerBase = (QSTileCustomizerBase) ((ViewController) QSTileCustomizerController.this).mView;
            QSTileCustomizerController qSTileCustomizerController = QSTileCustomizerController.this;
            SecCustomizeTileView secCustomizeTileView = qSTileCustomizerController.mLongClickedView;
            int i = qSTileCustomizerController.mWhereAmI;
            float f = qSTileCustomizerController.mPointX;
            float f2 = qSTileCustomizerController.mPointY;
            qSTileCustomizerBase.getClass();
            QSTileCustomizerBase.AnonymousClass1 anonymousClass1 = new View.DragShadowBuilder(qSTileCustomizerBase, secCustomizeTileView, f, f2) { // from class: com.android.systemui.qs.customize.view.QSTileCustomizerBase.1
                public final /* synthetic */ float val$pointX;
                public final /* synthetic */ float val$pointY;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(QSTileCustomizerBase qSTileCustomizerBase2, View secCustomizeTileView2, float f3, float f22) {
                    super(secCustomizeTileView2);
                    this.val$pointX = f3;
                    this.val$pointY = f22;
                }

                @Override // android.view.View.DragShadowBuilder
                public final void onProvideShadowMetrics(Point point, Point point2) {
                    point.set(getView().getWidth(), getView().getHeight());
                    point2.set((int) this.val$pointX, (int) this.val$pointY);
                }
            };
            boolean z = false;
            try {
                z = this.val$view.startDragAndDrop(new ClipData(new ClipDescription("", new String[0]), new ClipData.Item(new Intent())), anonymousClass1, null, 1048832);
            } catch (IllegalStateException unused) {
            }
            if (!z) {
                ((QSTileCustomizerBase) ((ViewController) QSTileCustomizerController.this).mView).requestLayout();
            }
            this.val$view.setAlpha(0.0f);
            this.val$view.postDelayed(new QSTileCustomizerController$$ExternalSyntheticLambda3(this, 1), 100L);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            QSTileCustomizerController.this.mLongClickedView.mLabelContainer.setVisibility(8);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
