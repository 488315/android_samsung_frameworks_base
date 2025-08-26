package com.android.systemui.qs.customize.viewcontroller;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomActionId;
import com.android.systemui.qs.customize.CustomActionManager;
import com.android.systemui.qs.customize.CustomActionMoveItem;
import com.android.systemui.qs.customize.CustomTileInfo;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.CustomizerTileViewPager.AnonymousClass4;
import com.android.systemui.qs.customize.SecQSCustomizerAnimator;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.QSFullTileCustomizer;
import com.android.systemui.qs.customize.view.QSPopOverFullTileCustomizer;
import com.android.systemui.qs.customize.view.QSPopOverTopTileCustomizer;
import com.android.systemui.qs.customize.view.QSTileCustomizerBase;
import com.android.systemui.qs.customize.view.QSTopTileCustomizer;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.pipeline.data.repository.TileNameConverter;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import kotlin.collections.builders.ListBuilder;

/* loaded from: classes2.dex */
public class QSTileCustomizerController extends ViewControllerBase {
    public final CustomizerTileViewPager mActiveTileLayout;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public final Context mContext;
    public CustomActionManager mCustomActionManager;
    public CustomActionMoveItem mCustomActionMoveItem;
    public int mCutoutBottomMargin;
    public int mCutoutTopMargin;
    public final View mDoneButton;
    public QSCMainViewController$showView$1$1 mDoneCallBack;
    public final QSTileCustomizerController$$ExternalSyntheticLambda0 mDoneOnClickListener;
    public final SecQSSettingEditResources mEditResources;
    public final QSTileCustomizerInteractionManager mInteractionManager;
    public final boolean mIsTopEdit;
    public final View mResetButton;
    public SystemUIDialog mResetDialog;
    public final AnonymousClass1 mResetOnClickListener;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SecQSCustomizerTileAdapter mTileAdapter;
    public final int mTopMinMaxNum;

    public static void $r8$lambda$j3XH0BZWhIZQf09t7hUyJd4ZIW4(QSTileCustomizerController qSTileCustomizerController) {
        if (((QSTileCustomizerBase) qSTileCustomizerController.mView).isShown()) {
            List spec = qSTileCustomizerController.mActiveTileLayout.getSpec();
            List quickPanelItems = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).getQuickPanelItems();
            int size = quickPanelItems != null ? ((ArrayList) quickPanelItems).size() : 0;
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

    /* renamed from: -$$Nest$mshowResetDialog, reason: not valid java name */
    public static void m2902$$Nest$mshowResetDialog(QSTileCustomizerController qSTileCustomizerController) {
        if (qSTileCustomizerController.mResetDialog == null) {
            SystemUIDialog systemUIDialog = new SystemUIDialog(((QSTileCustomizerBase) qSTileCustomizerController.mView).getContext(), R.style.Theme_SystemUI_Dialog_Alert);
            qSTileCustomizerController.mResetDialog = systemUIDialog;
            systemUIDialog.setMessage(R.string.sec_qs_edit_panel_reset_dialog_message);
            qSTileCustomizerController.mResetDialog.setPositiveButton(R.string.quick_settings_reset, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ArrayList arrayList;
                    int i2;
                    Log.d("QSTileCustomizerController", "RESET");
                    QSTileCustomizerController qSTileCustomizerController2 = QSTileCustomizerController.this;
                    StringBuilder sb = new StringBuilder("reset =  ");
                    SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = qSTileCustomizerController2.mTileAdapter;
                    sb.append(secQSCustomizerTileAdapter.mCurrentSpecs);
                    Log.d("SecQSCustomizerTileAdapter", sb.toString());
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    CustomizerTileViewPager customizerTileViewPager = qSTileCustomizerController2.mActiveTileLayout;
                    List spec = customizerTileViewPager.getSpec();
                    List defaultTileList = secQSCustomizerTileAdapter.mHost.getDefaultTileList();
                    Log.d("SecQSCustomizerTileAdapter", "cscTileList: " + defaultTileList);
                    arrayList2.addAll(customizerTileViewPager.getTilesInfo());
                    CustomizerTileViewPager customizerTileViewPager2 = qSTileCustomizerController2.mAvailableTileLayout;
                    arrayList2.addAll(customizerTileViewPager2.getTilesInfo());
                    ArrayList arrayList5 = (ArrayList) defaultTileList;
                    int size = arrayList5.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList5.get(i3);
                        i3++;
                        String str = (String) obj;
                        int size2 = arrayList2.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size2) {
                                arrayList = arrayList5;
                                i2 = size;
                                break;
                            }
                            Object obj2 = arrayList2.get(i4);
                            i4++;
                            CustomTileInfo customTileInfo = (CustomTileInfo) obj2;
                            arrayList = arrayList5;
                            i2 = size;
                            Log.i("SecQSCustomizerTileAdapter", "resetSpecs  old=" + customTileInfo.spec + "  default=" + str);
                            if (customTileInfo.spec.equals(str)) {
                                customTileInfo.isActive = true;
                                arrayList3.add(customTileInfo);
                                arrayList2.remove(customTileInfo);
                                arrayList4.add(str);
                                break;
                            }
                            arrayList5 = arrayList;
                            size = i2;
                        }
                        if (secQSCustomizerTileAdapter.mIsTopEdit && arrayList3.size() == ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQsTileMinNum(secQSCustomizerTileAdapter.mContext)) {
                            break;
                        }
                        arrayList5 = arrayList;
                        size = i2;
                    }
                    if (!arrayList4.equals(spec)) {
                        customizerTileViewPager.addTiles(arrayList3);
                        int size3 = arrayList2.size();
                        int i5 = 0;
                        while (i5 < size3) {
                            Object obj3 = arrayList2.get(i5);
                            i5++;
                            ((CustomTileInfo) obj3).isActive = false;
                        }
                        customizerTileViewPager2.addTiles(arrayList2);
                        secQSCustomizerTileAdapter.mIsReset = true;
                    }
                    QSTileCustomizerController.this.mInteractionManager.sendAnnouncementEvent(R.string.qs_reset_complete);
                    if (!((QSTileCustomizerBase) ((ViewController) QSTileCustomizerController.this).mView).isShown()) {
                        QSTileCustomizerController.this.save$1();
                    }
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), QSTileCustomizerController.this.mIsTopEdit ? SystemUIAnalytics.EID_EDIT_TOP_RESET : SystemUIAnalytics.EID_EDIT_RESET);
                }
            });
            qSTileCustomizerController.mResetDialog.setNegativeButton(R.string.no, null);
            qSTileCustomizerController.mResetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    QSTileCustomizerController.this.mResetDialog = null;
                }
            });
            qSTileCustomizerController.mResetDialog.show();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x01a5  */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public QSTileCustomizerController(Context context, SecQSSettingEditResources secQSSettingEditResources, boolean z, int i, int i2) throws Resources.NotFoundException {
        boolean z2;
        View viewRequireViewById;
        super((QpRune.QUICK_POP_OVER_CUSTOMIZER && z) ? secQSSettingEditResources.isCurrentTopEdit ? new QSPopOverTopTileCustomizer(context, i, i2) : new QSPopOverFullTileCustomizer(context, i, i2) : secQSSettingEditResources.isCurrentTopEdit ? new QSTopTileCustomizer(context, i, i2) : new QSFullTileCustomizer(context, i, i2));
        this.mDoneOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTileCustomizerController.$r8$lambda$j3XH0BZWhIZQf09t7hUyJd4ZIW4(this.f$0);
            }
        };
        this.mResetOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog = QSTileCustomizerController.this.mResetDialog;
                if (systemUIDialog == null || !systemUIDialog.isShowing()) {
                    QSTileCustomizerController.m2902$$Nest$mshowResetDialog(QSTileCustomizerController.this);
                }
            }
        };
        int i3 = 0;
        this.mCutoutTopMargin = 0;
        this.mCutoutBottomMargin = 0;
        QSTileCustomizerBase qSTileCustomizerBase = (QSTileCustomizerBase) this.mView;
        qSTileCustomizerBase.mEditResources = secQSSettingEditResources;
        this.mIsTopEdit = secQSSettingEditResources.isCurrentTopEdit;
        this.mContext = context;
        this.mResetButton = qSTileCustomizerBase.findViewById(R.id.left_button);
        this.mDoneButton = ((QSTileCustomizerBase) this.mView).findViewById(R.id.right_button);
        this.mCutoutTopMargin = i;
        this.mCutoutBottomMargin = i2;
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
                if (!((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                    view2.setTranslationY(displayHeight);
                }
                view2.setAlpha(0.0f);
            }
        }
        View view3 = SecQSCustomizerAnimator.mainView;
        if (view3 != null && (viewRequireViewById = view3.requireViewById(R.id.navigation_bar_view)) != null) {
            viewRequireViewById.setAlpha(0.0f);
        }
        View viewFindViewById = t.findViewById(R.id.qs_customize_top_summary_buttons);
        if (viewFindViewById != null) {
            viewFindViewById.setScaleY(0.95f);
            viewFindViewById.setScaleX(0.95f);
        }
        this.mEditResources = secQSSettingEditResources;
        CustomizerTileViewPager customizerTileViewPager = (CustomizerTileViewPager) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customizer_active_pager);
        this.mActiveTileLayout = customizerTileViewPager;
        customizerTileViewPager.setIsTopEdit(this.mIsTopEdit);
        CustomizerTileViewPager customizerTileViewPager2 = (CustomizerTileViewPager) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customizer_available_pager);
        this.mAvailableTileLayout = customizerTileViewPager2;
        customizerTileViewPager2.setIsTopEdit(this.mIsTopEdit);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mTopMinMaxNum = secQSPanelResourcePicker.getQsTileMinNum(((QSTileCustomizerBase) this.mView).getContext());
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = this.mIsTopEdit ? secQSSettingEditResources.tileTopAdapter : secQSSettingEditResources.tileFullAdapter;
        this.mTileAdapter = secQSCustomizerTileAdapter;
        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = new QSTileCustomizerInteractionManager((QSTileCustomizerBase) this.mView, secQSCustomizerTileAdapter);
        this.mInteractionManager = qSTileCustomizerInteractionManager;
        if (!this.mIsTopEdit) {
            List list = secQSCustomizerTileAdapter.mAllTiles;
            int size = list == null ? -1 : ((ArrayList) list).size();
            QSTileCustomizerBase qSTileCustomizerBase2 = (QSTileCustomizerBase) this.mView;
            size = size <= 0 ? 70 : size;
            int panelColumns = qSTileCustomizerBase2.mEditResources.getPanelColumns();
            qSTileCustomizerBase2.mActiveColumns = panelColumns;
            qSTileCustomizerBase2.mActiveRows = (size % panelColumns) + (size / panelColumns) + 1;
        }
        customizerTileViewPager.mInitialPagenum = secQSCustomizerTileAdapter.mActiveCurrentPage;
        customizerTileViewPager2.mInitialPagenum = secQSCustomizerTileAdapter.mAvailableCurrentPage;
        updateBlur();
        ((QSTileCustomizerBase) this.mView).updateResources();
        ArrayList arrayList = secQSCustomizerTileAdapter.mActiveTiles;
        if (arrayList != null) {
            customizerTileViewPager.addTiles(arrayList);
            ArrayList arrayList2 = secQSCustomizerTileAdapter.mActiveTiles;
            int size2 = arrayList2.size();
            int i4 = 0;
            while (i4 < size2) {
                Object obj = arrayList2.get(i4);
                i4++;
                ((CustomTileInfo) obj).longClickListener = qSTileCustomizerInteractionManager.longClickListener;
            }
        }
        SecQSSettingEditResources secQSSettingEditResources2 = this.mEditResources;
        if (this.mIsTopEdit) {
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSSettingEditResources2.tileTopAdapter;
            z2 = secQSCustomizerTileAdapter2 != null ? secQSCustomizerTileAdapter2.mIsLoadedAllTiles : false;
        } else {
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter3 = secQSSettingEditResources2.tileFullAdapter;
            if (secQSCustomizerTileAdapter3 != null) {
                z2 = secQSCustomizerTileAdapter3.mIsLoadedAllTiles;
            }
        }
        if (z2) {
            ArrayList arrayList3 = secQSCustomizerTileAdapter.mAvailableTiles;
            if (arrayList3 != null) {
                secQSCustomizerTileAdapter.mOnTileChangedCallback = null;
                this.mAvailableTileLayout.addTiles(arrayList3);
                ArrayList arrayList4 = secQSCustomizerTileAdapter.mAvailableTiles;
                int size3 = arrayList4.size();
                while (i3 < size3) {
                    Object obj2 = arrayList4.get(i3);
                    i3++;
                    ((CustomTileInfo) obj2).longClickListener = qSTileCustomizerInteractionManager.longClickListener;
                }
            }
        } else {
            secQSCustomizerTileAdapter.mOnTileChangedCallback = new QSTileCustomizerController$$ExternalSyntheticLambda3(this);
        }
        this.message = 100;
    }

    public static boolean isLargeScreen$10() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void close() {
        if (this.isShown) {
            super.close();
            ((QSTileCustomizerBase) this.mView).getClass();
            Log.d("QSTileCustomizerBase", "close customizer");
            SecQSCustomizerAnimator.Companion companion = SecQSCustomizerAnimator.Companion;
            final QSCMainViewController$showView$1$1 qSCMainViewController$showView$1$1 = this.doneCallback;
            T t = this.mView;
            companion.getClass();
            int displayHeight = (int) (DeviceState.getDisplayHeight(t.getContext()) * 0.0625d);
            ListBuilder activeTileContents = SecQSCustomizerAnimator.Companion.getActiveTileContents(t);
            ListBuilder availableTileContents = SecQSCustomizerAnimator.Companion.getAvailableTileContents(t);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(activeTileContents);
            arrayList2.addAll(availableTileContents);
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder((View) obj, PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, displayHeight));
                objectAnimatorOfPropertyValuesHolder.setDuration(200L);
                arrayList.add(objectAnimatorOfPropertyValuesHolder);
            }
            ObjectAnimator objectAnimatorOfPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(t.findViewById(R.id.qs_customize_top_summary_buttons), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 0.0f));
            objectAnimatorOfPropertyValuesHolder2.setDuration(200L);
            arrayList.add(objectAnimatorOfPropertyValuesHolder2);
            View view = SecQSCustomizerAnimator.mainView;
            if (view != null) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view.requireViewById(R.id.navigation_bar_view), (Property<View, Float>) View.ALPHA, 0.0f);
                objectAnimatorOfFloat.setDuration(200L);
                arrayList.add(objectAnimatorOfFloat);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            if (qSCMainViewController$showView$1$1 != null) {
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.customize.SecQSCustomizerAnimator$Companion$playAnimatorSet$1$1$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        qSCMainViewController$showView$1$1.run();
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
            CustomActionMoveItem customActionMoveItem = this.mCustomActionMoveItem;
            if (customActionMoveItem != null) {
                customActionMoveItem.actionFinish();
            }
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        ViewPropertyAnimator viewPropertyAnimatorAnimate;
        ViewPropertyAnimator viewPropertyAnimatorScaleY;
        ViewPropertyAnimator viewPropertyAnimatorScaleX;
        ViewPropertyAnimator duration;
        View viewRequireViewById;
        ViewPropertyAnimator viewPropertyAnimatorAnimate2;
        ViewPropertyAnimator viewPropertyAnimatorAlpha;
        ViewPropertyAnimator duration2;
        ViewPropertyAnimator viewPropertyAnimatorAnimate3;
        ViewPropertyAnimator viewPropertyAnimatorTranslationY;
        ViewPropertyAnimator viewPropertyAnimatorAlpha2;
        ViewPropertyAnimator duration3;
        ViewPropertyAnimator viewPropertyAnimatorAnimate4;
        ViewPropertyAnimator viewPropertyAnimatorAlpha3;
        ViewPropertyAnimator duration4;
        this.mResetButton.setOnClickListener(this.mResetOnClickListener);
        this.mDoneButton.setOnClickListener(this.mDoneOnClickListener);
        this.mCustomActionManager = new CustomActionManager();
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        int panelSidePadding = secQSPanelResourcePicker.getPanelSidePadding(context);
        boolean z = QpRune.QUICK_POP_OVER_CUSTOMIZER;
        if (z && isLargeScreen$10()) {
            panelSidePadding = secQSPanelResourcePicker.getPopOverMargin(this.mContext);
        }
        View viewFindViewById = ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customize_top_summary_buttons);
        viewFindViewById.setPadding(panelSidePadding, viewFindViewById.getPaddingTop(), panelSidePadding, viewFindViewById.getPaddingBottom());
        if (z && isLargeScreen$10()) {
            FrameLayout frameLayout = (FrameLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customize_top_summary_buttons_button_area);
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_buttons_top_margin);
            if (frameLayout != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, dimensionPixelSize, layoutParams.rightMargin, layoutParams.bottomMargin);
                frameLayout.setLayoutParams(layoutParams);
            }
        }
        TextView textView = (TextView) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_edit_summary);
        textView.setSelected(true);
        textView.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
            }
        });
        ((TextView) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_edit_more_summary)).setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController.5
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
            }
        });
        QSTileCustomizerInteractionManager qSTileCustomizerInteractionManager = this.mInteractionManager;
        QSTileCustomizerInteractionManager$initializeListeners$2 qSTileCustomizerInteractionManager$initializeListeners$2 = qSTileCustomizerInteractionManager.dragListener;
        CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
        customizerTileViewPager.mDragListener = qSTileCustomizerInteractionManager$initializeListeners$2;
        customizerTileViewPager.mClickListener = qSTileCustomizerInteractionManager.clickListener;
        View viewFindViewById2 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_edit_available_text);
        View viewFindViewById3 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_customize_top_summary_buttons);
        View viewFindViewById4 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.scroll_top_area);
        View viewFindViewById5 = ((QSTileCustomizerBase) this.mView).findViewById(R.id.scroll_bottom_area);
        viewFindViewById2.setTag(9999);
        viewFindViewById3.setTag(9999);
        viewFindViewById4.setTag(9999);
        viewFindViewById5.setTag(9999);
        viewFindViewById2.setOnDragListener(qSTileCustomizerInteractionManager.dragListener);
        viewFindViewById3.setOnDragListener(qSTileCustomizerInteractionManager.dragListener);
        viewFindViewById4.setOnDragListener(qSTileCustomizerInteractionManager.dragListener);
        viewFindViewById5.setOnDragListener(qSTileCustomizerInteractionManager.dragListener);
        customizerTileViewPager.mCustomActionManager = this.mCustomActionManager;
        SecPageIndicator secPageIndicator = (SecPageIndicator) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_available_paged_indicator);
        View viewRequireViewById2 = ((QSTileCustomizerBase) this.mView).requireViewById(R.id.customize_container);
        CustomizerTileViewPager customizerTileViewPager2 = this.mAvailableTileLayout;
        customizerTileViewPager2.mParentContainer = viewRequireViewById2;
        customizerTileViewPager2.mPageIndicator = secPageIndicator;
        secPageIndicator.setLocation(customizerTileViewPager2.mPageIndicatorPosition);
        SecPageIndicator secPageIndicator2 = customizerTileViewPager2.mPageIndicator;
        secPageIndicator2.mQsExpansion = 1.0f;
        secPageIndicator2.mCallback = customizerTileViewPager2.new AnonymousClass4();
        customizerTileViewPager2.mDragListener = qSTileCustomizerInteractionManager.dragListener;
        customizerTileViewPager2.mClickListener = qSTileCustomizerInteractionManager.clickListener;
        int color = getContext().getColor(R.color.qs_edit_available_page_indicator_tint_color_selected);
        int color2 = getContext().getColor(R.color.qs_edit_available_page_indicator_tint_color_unselected);
        SecPageIndicator secPageIndicator3 = customizerTileViewPager2.mPageIndicator;
        if (secPageIndicator3 != null) {
            secPageIndicator3.mSelectedColor = color;
            secPageIndicator3.mUnselectedColor = color2;
        }
        CustomActionManager customActionManager = this.mCustomActionManager;
        customizerTileViewPager2.mCustomActionManager = customActionManager;
        final int i = 0;
        customActionManager.setCustomAction(CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE, new Consumer(this) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda1
            public final /* synthetic */ QSTileCustomizerController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                QSTileCustomizerController qSTileCustomizerController = this.f$0;
                View view = (View) obj;
                switch (i2) {
                    case 0:
                        CustomActionMoveItem customActionMoveItem = qSTileCustomizerController.mCustomActionMoveItem;
                        if (customActionMoveItem != null) {
                            customActionMoveItem.actionFinish();
                        }
                        qSTileCustomizerController.mCustomActionMoveItem = qSTileCustomizerController.mInteractionManager.createCustomActionMoveItem((CustomTileInfo) view.getTag(), qSTileCustomizerController.mAvailableTileLayout, qSTileCustomizerController.mActiveTileLayout);
                        break;
                    default:
                        CustomActionMoveItem customActionMoveItem2 = qSTileCustomizerController.mCustomActionMoveItem;
                        if (customActionMoveItem2 != null) {
                            customActionMoveItem2.actionFinish();
                        }
                        qSTileCustomizerController.mCustomActionMoveItem = qSTileCustomizerController.mInteractionManager.createCustomActionMoveItem((CustomTileInfo) view.getTag(), qSTileCustomizerController.mAvailableTileLayout, qSTileCustomizerController.mActiveTileLayout);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mCustomActionManager.setCustomAction(CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE, new Consumer(this) { // from class: com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda1
            public final /* synthetic */ QSTileCustomizerController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                QSTileCustomizerController qSTileCustomizerController = this.f$0;
                View view = (View) obj;
                switch (i22) {
                    case 0:
                        CustomActionMoveItem customActionMoveItem = qSTileCustomizerController.mCustomActionMoveItem;
                        if (customActionMoveItem != null) {
                            customActionMoveItem.actionFinish();
                        }
                        qSTileCustomizerController.mCustomActionMoveItem = qSTileCustomizerController.mInteractionManager.createCustomActionMoveItem((CustomTileInfo) view.getTag(), qSTileCustomizerController.mAvailableTileLayout, qSTileCustomizerController.mActiveTileLayout);
                        break;
                    default:
                        CustomActionMoveItem customActionMoveItem2 = qSTileCustomizerController.mCustomActionMoveItem;
                        if (customActionMoveItem2 != null) {
                            customActionMoveItem2.actionFinish();
                        }
                        qSTileCustomizerController.mCustomActionMoveItem = qSTileCustomizerController.mInteractionManager.createCustomActionMoveItem((CustomTileInfo) view.getTag(), qSTileCustomizerController.mAvailableTileLayout, qSTileCustomizerController.mActiveTileLayout);
                        break;
                }
            }
        });
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
            if (view != null && (viewPropertyAnimatorAnimate4 = view.animate()) != null && (viewPropertyAnimatorAlpha3 = viewPropertyAnimatorAnimate4.alpha(1.0f)) != null && (duration4 = viewPropertyAnimatorAlpha3.setDuration(100L)) != null) {
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
            if (view2 != null && (viewPropertyAnimatorAnimate3 = view2.animate()) != null && (viewPropertyAnimatorTranslationY = viewPropertyAnimatorAnimate3.translationY(0.0f)) != null && (viewPropertyAnimatorAlpha2 = viewPropertyAnimatorTranslationY.alpha(1.0f)) != null && (duration3 = viewPropertyAnimatorAlpha2.setDuration(200L)) != null) {
                duration3.start();
            }
        }
        View view3 = SecQSCustomizerAnimator.mainView;
        if (view3 != null && (viewRequireViewById = view3.requireViewById(R.id.navigation_bar_view)) != null && (viewPropertyAnimatorAnimate2 = viewRequireViewById.animate()) != null && (viewPropertyAnimatorAlpha = viewPropertyAnimatorAnimate2.alpha(1.0f)) != null && (duration2 = viewPropertyAnimatorAlpha.setDuration(200L)) != null) {
            duration2.start();
        }
        View viewFindViewById6 = qSTileCustomizerBase.findViewById(R.id.qs_customize_top_summary_buttons);
        if (viewFindViewById6 == null || (viewPropertyAnimatorAnimate = viewFindViewById6.animate()) == null || (viewPropertyAnimatorScaleY = viewPropertyAnimatorAnimate.scaleY(1.0f)) == null || (viewPropertyAnimatorScaleX = viewPropertyAnimatorScaleY.scaleX(1.0f)) == null || (duration = viewPropertyAnimatorScaleX.setDuration(200L)) == null) {
            return;
        }
        duration.start();
    }

    public final void removeBlurs() {
        LinearLayout linearLayout = (LinearLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_available_area);
        LinearLayout linearLayout2 = (LinearLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_active_page_parent);
        if (linearLayout != null) {
            linearLayout.semSetBlurInfo(null);
        }
        if (linearLayout2 != null) {
            linearLayout2.semSetBlurInfo(null);
        }
    }

    public final void save$1() {
        String tileLoggingName;
        String str;
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = this.mTileAdapter;
        if (secQSCustomizerTileAdapter.mTileQueryHelper.mFinished) {
            Log.d("SecQSCustomizerTileAdapter", "mCurrentSpecs =  " + secQSCustomizerTileAdapter.mCurrentSpecs);
            CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
            List spec = customizerTileViewPager.getSpec();
            boolean zEquals = ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).equals(spec);
            boolean z = secQSCustomizerTileAdapter.mIsTopEdit;
            String str2 = SystemUIAnalytics.EID_EDIT_DONE;
            if (zEquals) {
                Log.d("SecQSCustomizerTileAdapter", "save none : same list");
                String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
                if (z) {
                    str2 = SystemUIAnalytics.EID_EDIT_TOP_DONE;
                }
                SystemUIAnalytics.sendRunestoneEventCDLog(currentScreenID, str2, "isChanged", "false", SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
                return;
            }
            String currentScreenID2 = SystemUIAnalytics.getCurrentScreenID();
            if (z) {
                str2 = SystemUIAnalytics.EID_EDIT_TOP_DONE;
            }
            SystemUIAnalytics.sendRunestoneEventCDLog(currentScreenID2, str2, "isChanged", "true", SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            if (z) {
                Prefs.putBoolean(secQSCustomizerTileAdapter.mContext, "QQsHasEditedQuickTileList", true);
            } else {
                Prefs.putBoolean(secQSCustomizerTileAdapter.mContext, "QsHasEditedQuickTileList", true);
            }
            secQSCustomizerTileAdapter.mHost.changeTilesByUser(secQSCustomizerTileAdapter.mCurrentSpecs, spec, secQSCustomizerTileAdapter.mIsReset);
            secQSCustomizerTileAdapter.mDefaultActiveTiles = customizerTileViewPager.getTilesInfo();
            ArrayList tilesInfo = this.mAvailableTileLayout.getTilesInfo();
            secQSCustomizerTileAdapter.mActiveTiles = secQSCustomizerTileAdapter.mDefaultActiveTiles;
            secQSCustomizerTileAdapter.mAvailableTiles = tilesInfo;
            int i = 0;
            secQSCustomizerTileAdapter.mActiveCurrentPage = 0;
            secQSCustomizerTileAdapter.mAvailableCurrentPage = 0;
            ArrayList arrayList = (ArrayList) spec;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                String str3 = (String) obj;
                if (!((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).contains(str3)) {
                    if (str3.startsWith("custom(")) {
                        TileNameConverter tileNameConverter = TileNameConverter.INSTANCE;
                        Resources resources = secQSCustomizerTileAdapter.mContext.getResources();
                        tileNameConverter.getClass();
                        String tileLoggingName2 = TileNameConverter.toTileLoggingName(resources, str3);
                        if (tileLoggingName2 == null) {
                            tileLoggingName2 = CustomTile.getComponentFromSpec(str3).toShortString();
                        }
                        str = tileLoggingName2;
                    } else {
                        str = str3;
                    }
                    String strValueOf = String.valueOf(arrayList.indexOf(str3) + 1);
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), z ? SystemUIAnalytics.EID_EDIT_TOP_MOVE_TO_ACTIVE : SystemUIAnalytics.EID_EDIT_MOVE_TO_ACTIVE, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_NAME, str, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, strValueOf);
                    MediaSessions$H$$ExternalSyntheticOutline0.m("save add : ", str, " ", strValueOf, "SecQSCustomizerTileAdapter");
                }
            }
            ArrayList arrayList2 = (ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                String str4 = (String) obj2;
                if (str4.startsWith("custom(")) {
                    TileNameConverter tileNameConverter2 = TileNameConverter.INSTANCE;
                    Resources resources2 = secQSCustomizerTileAdapter.mContext.getResources();
                    tileNameConverter2.getClass();
                    tileLoggingName = TileNameConverter.toTileLoggingName(resources2, str4);
                    if (tileLoggingName == null) {
                        tileLoggingName = CustomTile.getComponentFromSpec(str4).toShortString();
                    }
                } else {
                    tileLoggingName = str4;
                }
                if (!arrayList.contains(str4)) {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), z ? SystemUIAnalytics.EID_EDIT_TOP_MOVE_TO_AVAILABLE : SystemUIAnalytics.EID_EDIT_MOVE_TO_AVAILABLE, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_NAME, tileLoggingName);
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("save remove : "), tileLoggingName, "SecQSCustomizerTileAdapter");
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
        this.mDoneCallBack = (QSCMainViewController$showView$1$1) runnable;
    }

    public final void updateBlur() throws Resources.NotFoundException {
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) != 0;
        LinearLayout linearLayout = (LinearLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_available_area);
        LinearLayout linearLayout2 = (LinearLayout) ((QSTileCustomizerBase) this.mView).findViewById(R.id.qs_active_page_parent);
        if (isLargeScreen$10()) {
            linearLayout.setTranslationZ(1.0f);
            linearLayout2.setTranslationZ(1.0f);
        } else {
            linearLayout.setTranslationZ(0.0f);
            linearLayout2.setTranslationZ(0.0f);
        }
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            linearLayout.setBackgroundResource(R.drawable.qs_customizer_bg_available_area_tablet);
        }
        if (z) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_area_corner_radius);
        this.mEditResources.getClass();
        boolean zIsBarPhone = SecQSSettingEditResources.isBarPhone();
        float f = dimensionPixelSize;
        linearLayout.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(14).setBackgroundCornerRadius(f, f, !zIsBarPhone ? f : 0.0f, zIsBarPhone ? 0.0f : f).setRadius(200).build());
        if (this.mIsTopEdit) {
            linearLayout2.semSetBlurInfo(null);
        } else {
            linearLayout2.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(14).setBackgroundCornerRadius(f).build());
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void windowInsetChanged(int i, int i2) throws Resources.NotFoundException {
        boolean z = QpRune.QUICK_POP_OVER_CUSTOMIZER;
        if (z && isLargeScreen$10()) {
            if (this.mCutoutTopMargin == i && this.mCutoutBottomMargin == i2) {
                return;
            }
            this.mCutoutTopMargin = i;
            this.mCutoutBottomMargin = i2;
            QSTileCustomizerBase qSTileCustomizerBase = (QSTileCustomizerBase) this.mView;
            qSTileCustomizerBase.getClass();
            if (z && QSTileCustomizerBase.isLargeScreen() && (qSTileCustomizerBase.mCutoutTopMargin != i || qSTileCustomizerBase.mCutoutBottomMargin != i2)) {
                qSTileCustomizerBase.mCutoutTopMargin = i;
                qSTileCustomizerBase.mCutoutBottomMargin = i2;
                qSTileCustomizerBase.calculateAvailableArea();
            }
            updateBlur();
        }
    }
}
