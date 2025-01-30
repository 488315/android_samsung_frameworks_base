package com.android.systemui.privacy.television;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.privacy.television.PrivacyItemsChip;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPrivacyChipsController implements CoreStartable, PrivacyItemController.Callback {
    public static final List CHIPS = Arrays.asList(new PrivacyItemsChip.ChipConfig(Collections.singletonList(PrivacyType.TYPE_MEDIA_PROJECTION), R.color.privacy_media_projection_chip, false), new PrivacyItemsChip.ChipConfig(Arrays.asList(PrivacyType.TYPE_CAMERA, PrivacyType.TYPE_MICROPHONE), R.color.privacy_mic_cam_chip, true));
    public List mChips;
    public ViewGroup mChipsContainer;
    public final TransitionSet mCollapseTransition;
    public final Context mContext;
    public final IWindowManager mIWindowManager;
    public boolean mIsRtl;
    public final PrivacyItemController mPrivacyItemController;
    public final TransitionSet mTransition;
    public final Handler mUiThreadHandler = new Handler(Looper.getMainLooper());
    public final TvPrivacyChipsController$$ExternalSyntheticLambda1 mCollapseRunnable = new Runnable() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            TvPrivacyChipsController tvPrivacyChipsController = TvPrivacyChipsController.this;
            ViewGroup viewGroup = tvPrivacyChipsController.mChipsContainer;
            if (viewGroup == null) {
                return;
            }
            TransitionManager.beginDelayedTransition(viewGroup, tvPrivacyChipsController.mCollapseTransition);
            Iterator it = ((ArrayList) tvPrivacyChipsController.mChips).iterator();
            while (it.hasNext()) {
                PrivacyItemsChip privacyItemsChip = (PrivacyItemsChip) it.next();
                int i = privacyItemsChip.mState;
                if (i == 1) {
                    if (i != 2) {
                        privacyItemsChip.mState = 2;
                    }
                    Iterator it2 = ((ArrayList) privacyItemsChip.mIcons).iterator();
                    while (it2.hasNext()) {
                        ImageView imageView = (ImageView) it2.next();
                        if (privacyItemsChip.mConfig.collapseToDot) {
                            imageView.setVisibility(8);
                        } else {
                            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                            int i2 = privacyItemsChip.mCollapsedIconSize;
                            layoutParams.width = i2;
                            layoutParams.height = i2;
                            imageView.requestLayout();
                        }
                    }
                    PrivacyChipDrawable privacyChipDrawable = privacyItemsChip.mChipBackgroundDrawable;
                    if (privacyChipDrawable.mIsExpanded) {
                        privacyChipDrawable.mIsExpanded = false;
                        privacyChipDrawable.mExpand.cancel();
                        privacyChipDrawable.mCollapse.start();
                    }
                }
            }
        }
    };
    public final TvPrivacyChipsController$$ExternalSyntheticLambda2 mUpdatePrivacyItemsRunnable = new Runnable() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            TvPrivacyChipsController tvPrivacyChipsController = TvPrivacyChipsController.this;
            tvPrivacyChipsController.updateChips();
            Handler handler = tvPrivacyChipsController.mUiThreadHandler;
            TvPrivacyChipsController$$ExternalSyntheticLambda3 tvPrivacyChipsController$$ExternalSyntheticLambda3 = tvPrivacyChipsController.mAccessibilityRunnable;
            handler.removeCallbacks(tvPrivacyChipsController$$ExternalSyntheticLambda3);
            if (tvPrivacyChipsController.mPrivacyItems.size() == 0) {
                tvPrivacyChipsController.makeAccessibilityAnnouncement();
            } else {
                handler.postDelayed(tvPrivacyChipsController$$ExternalSyntheticLambda3, 500L);
            }
        }
    };
    public final TvPrivacyChipsController$$ExternalSyntheticLambda3 mAccessibilityRunnable = new Runnable() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            TvPrivacyChipsController.this.makeAccessibilityAnnouncement();
        }
    };
    public final Rect[] mBounds = new Rect[4];
    public List mPrivacyItems = Collections.emptyList();
    public final List mItemsBeforeLastAnnouncement = new ArrayList();

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda3] */
    public TvPrivacyChipsController(Context context, PrivacyItemController privacyItemController, IWindowManager iWindowManager) {
        this.mContext = context;
        this.mPrivacyItemController = privacyItemController;
        this.mIWindowManager = iWindowManager;
        this.mIsRtl = context.getResources().getConfiguration().getLayoutDirection() == 1;
        updateStaticPrivacyIndicatorBounds();
        Interpolator loadInterpolator = AnimationUtils.loadInterpolator(context, R.interpolator.tv_privacy_chip_collapse_interpolator);
        Interpolator loadInterpolator2 = AnimationUtils.loadInterpolator(context, R.interpolator.tv_privacy_chip_expand_interpolator);
        TransitionSet addTransition = new TransitionSet().addTransition(new Fade(1)).addTransition(new Fade(2));
        addTransition.setOrdering(0);
        addTransition.excludeTarget(ImageView.class, true);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.excludeTarget(ImageView.class, true);
        changeBounds.setInterpolator(loadInterpolator2);
        ChangeBounds changeBounds2 = new ChangeBounds();
        changeBounds2.excludeTarget(ImageView.class, true);
        changeBounds2.setInterpolator(loadInterpolator);
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setOrdering(0);
        autoTransition.addTarget(ImageView.class);
        autoTransition.setInterpolator((TimeInterpolator) loadInterpolator);
        AutoTransition autoTransition2 = new AutoTransition();
        autoTransition2.setOrdering(0);
        autoTransition2.addTarget(ImageView.class);
        autoTransition2.setInterpolator((TimeInterpolator) loadInterpolator2);
        TransitionSet duration = new TransitionSet().addTransition(addTransition).addTransition(changeBounds).addTransition(autoTransition2).setOrdering(0).setDuration(r9.getInteger(R.integer.privacy_chip_animation_millis));
        this.mTransition = duration;
        TransitionSet duration2 = new TransitionSet().addTransition(addTransition).addTransition(changeBounds2).addTransition(autoTransition).setOrdering(0).setDuration(r9.getInteger(R.integer.privacy_chip_animation_millis));
        this.mCollapseTransition = duration2;
        Transition.TransitionListener transitionListener = new Transition.TransitionListener() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController.1
            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                List list = TvPrivacyChipsController.this.mChips;
                if (list != null) {
                    Iterator it = ((ArrayList) list).iterator();
                    boolean z = false;
                    boolean z2 = false;
                    while (it.hasNext()) {
                        PrivacyItemsChip privacyItemsChip = (PrivacyItemsChip) it.next();
                        z = z || privacyItemsChip.getVisibility() == 0;
                        if (!z2) {
                            if (!(privacyItemsChip.mState == 1)) {
                                z2 = false;
                            }
                        }
                        z2 = true;
                    }
                    if (!z) {
                        TvPrivacyChipsController.this.removeIndicatorView();
                        return;
                    }
                    if (z2) {
                        TvPrivacyChipsController tvPrivacyChipsController = TvPrivacyChipsController.this;
                        Handler handler = tvPrivacyChipsController.mUiThreadHandler;
                        TvPrivacyChipsController$$ExternalSyntheticLambda1 tvPrivacyChipsController$$ExternalSyntheticLambda1 = tvPrivacyChipsController.mCollapseRunnable;
                        handler.removeCallbacks(tvPrivacyChipsController$$ExternalSyntheticLambda1);
                        handler.postDelayed(tvPrivacyChipsController$$ExternalSyntheticLambda1, 4000L);
                    }
                }
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionPause(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionResume(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
            }
        };
        duration.addListener(transitionListener);
        duration2.addListener(transitionListener);
    }

    public static boolean listContainsPrivacyType(List list, PrivacyType privacyType) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((PrivacyItem) it.next()).privacyType == privacyType) {
                return true;
            }
        }
        return false;
    }

    public final void createAndShowIndicator() {
        Context context = this.mContext;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.PrivacyChip);
        this.mChips = new ArrayList();
        this.mChipsContainer = (ViewGroup) LayoutInflater.from(contextThemeWrapper).inflate(R.layout.tv_privacy_chip_container, (ViewGroup) null);
        int dimensionPixelSize = contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen.privacy_chip_margin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMarginStart(dimensionPixelSize);
        layoutParams.setMarginEnd(dimensionPixelSize);
        Iterator it = CHIPS.iterator();
        while (it.hasNext()) {
            PrivacyItemsChip privacyItemsChip = new PrivacyItemsChip(contextThemeWrapper, (PrivacyItemsChip.ChipConfig) it.next());
            this.mChipsContainer.addView(privacyItemsChip, layoutParams);
            this.mChips.add(privacyItemsChip);
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        ViewGroup viewGroup = this.mChipsContainer;
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(-2, -2, 2006, 8, -3);
        layoutParams2.gravity = (this.mIsRtl ? 3 : 5) | 48;
        layoutParams2.setTitle("MicrophoneCaptureIndicator");
        layoutParams2.packageName = context.getPackageName();
        windowManager.addView(viewGroup, layoutParams2);
        final ViewGroup viewGroup2 = this.mChipsContainer;
        viewGroup2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                viewGroup2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TvPrivacyChipsController tvPrivacyChipsController = TvPrivacyChipsController.this;
                List list = TvPrivacyChipsController.CHIPS;
                tvPrivacyChipsController.updateChips();
            }
        });
    }

    public final void makeAccessibilityAnnouncement() {
        int i;
        if (this.mChipsContainer == null) {
            return;
        }
        List list = this.mItemsBeforeLastAnnouncement;
        PrivacyType privacyType = PrivacyType.TYPE_CAMERA;
        boolean listContainsPrivacyType = listContainsPrivacyType(list, privacyType);
        boolean listContainsPrivacyType2 = listContainsPrivacyType(this.mPrivacyItems, privacyType);
        PrivacyType privacyType2 = PrivacyType.TYPE_MICROPHONE;
        boolean listContainsPrivacyType3 = listContainsPrivacyType(list, privacyType2);
        boolean listContainsPrivacyType4 = listContainsPrivacyType(this.mPrivacyItems, privacyType2);
        PrivacyType privacyType3 = PrivacyType.TYPE_MEDIA_PROJECTION;
        boolean listContainsPrivacyType5 = listContainsPrivacyType(list, privacyType3);
        boolean listContainsPrivacyType6 = listContainsPrivacyType(this.mPrivacyItems, privacyType3);
        Context context = this.mContext;
        if (!listContainsPrivacyType && listContainsPrivacyType2 && !listContainsPrivacyType3 && listContainsPrivacyType4) {
            i = R.string.mic_and_camera_recording_announcement;
        } else if (!listContainsPrivacyType || listContainsPrivacyType2 || !listContainsPrivacyType3 || listContainsPrivacyType4) {
            i = (!listContainsPrivacyType || listContainsPrivacyType2) ? (listContainsPrivacyType || !listContainsPrivacyType2) ? 0 : R.string.camera_recording_announcement : R.string.camera_stopped_recording_announcement;
            if (i != 0) {
                this.mChipsContainer.announceForAccessibility(context.getString(i));
                i = 0;
            }
            if (listContainsPrivacyType3 && !listContainsPrivacyType4) {
                i = R.string.mic_stopped_recording_announcement;
            } else if (!listContainsPrivacyType3 && listContainsPrivacyType4) {
                i = R.string.mic_recording_announcement;
            }
        } else {
            i = R.string.mic_camera_stopped_recording_announcement;
        }
        if (i != 0) {
            this.mChipsContainer.announceForAccessibility(context.getString(i));
        }
        if (!listContainsPrivacyType5 && listContainsPrivacyType6) {
            this.mChipsContainer.announceForAccessibility(context.getString(R.string.screen_recording_announcement));
        } else if (listContainsPrivacyType5 && !listContainsPrivacyType6) {
            this.mChipsContainer.announceForAccessibility(context.getString(R.string.screen_stopped_recording_announcement));
        }
        ArrayList arrayList = (ArrayList) list;
        arrayList.clear();
        arrayList.addAll(this.mPrivacyItems);
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z = configuration.getLayoutDirection() == 1;
        if (this.mIsRtl == z) {
            return;
        }
        this.mIsRtl = z;
        if (this.mChipsContainer != null) {
            removeIndicatorView();
            createAndShowIndicator();
        }
        updateStaticPrivacyIndicatorBounds();
    }

    @Override // com.android.systemui.privacy.PrivacyItemController.Callback
    public final void onPrivacyItemsChanged(List list) {
        new ArrayList(list).removeIf(new Predicate() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean z;
                TvPrivacyChipsController.this.getClass();
                PrivacyType privacyType = ((PrivacyItem) obj).privacyType;
                Iterator it = TvPrivacyChipsController.CHIPS.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (((PrivacyItemsChip.ChipConfig) it.next()).privacyTypes.contains(privacyType)) {
                        z = true;
                        break;
                    }
                }
                return !z;
            }
        });
        if (list.size() == this.mPrivacyItems.size() && this.mPrivacyItems.containsAll(list)) {
            return;
        }
        this.mPrivacyItems = list;
        Handler handler = this.mUiThreadHandler;
        TvPrivacyChipsController$$ExternalSyntheticLambda2 tvPrivacyChipsController$$ExternalSyntheticLambda2 = this.mUpdatePrivacyItemsRunnable;
        if (handler.hasCallbacks(tvPrivacyChipsController$$ExternalSyntheticLambda2)) {
            return;
        }
        handler.postDelayed(tvPrivacyChipsController$$ExternalSyntheticLambda2, 200L);
    }

    public final void removeIndicatorView() {
        ViewGroup viewGroup;
        this.mUiThreadHandler.removeCallbacks(this.mCollapseRunnable);
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        if (windowManager != null && (viewGroup = this.mChipsContainer) != null) {
            windowManager.removeView(viewGroup);
        }
        this.mChipsContainer = null;
        this.mChips = null;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mPrivacyItemController.addCallback(this);
    }

    public final void updateChips() {
        if (this.mChipsContainer == null) {
            if (this.mPrivacyItems.isEmpty()) {
                return;
            }
            createAndShowIndicator();
            return;
        }
        final ArraySet arraySet = new ArraySet();
        final int i = 0;
        this.mPrivacyItems.forEach(new Consumer() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        arraySet.add(((PrivacyItem) obj).privacyType);
                        break;
                    default:
                        Set set = arraySet;
                        PrivacyItemsChip privacyItemsChip = (PrivacyItemsChip) obj;
                        int i2 = 0;
                        boolean z = false;
                        while (true) {
                            int i3 = 8;
                            if (i2 >= privacyItemsChip.mConfig.privacyTypes.size()) {
                                if (!z) {
                                    privacyItemsChip.setVisibility(8);
                                    if (privacyItemsChip.mState != 0) {
                                        privacyItemsChip.mState = 0;
                                        break;
                                    }
                                } else {
                                    int i4 = privacyItemsChip.mState;
                                    if (i4 == 0) {
                                        PrivacyChipDrawable privacyChipDrawable = privacyItemsChip.mChipBackgroundDrawable;
                                        if (!privacyChipDrawable.mIsExpanded) {
                                            privacyChipDrawable.mIsExpanded = true;
                                            privacyChipDrawable.mCollapseProgress = 0.0f;
                                            privacyChipDrawable.invalidateSelf();
                                        }
                                    } else if (i4 == 2) {
                                        PrivacyChipDrawable privacyChipDrawable2 = privacyItemsChip.mChipBackgroundDrawable;
                                        if (!privacyChipDrawable2.mIsExpanded) {
                                            privacyChipDrawable2.mIsExpanded = true;
                                            privacyChipDrawable2.mCollapse.cancel();
                                            privacyChipDrawable2.mExpand.start();
                                        }
                                    }
                                    privacyItemsChip.setVisibility(0);
                                    if (privacyItemsChip.mState != 1) {
                                        privacyItemsChip.mState = 1;
                                        break;
                                    }
                                }
                            } else {
                                PrivacyType privacyType = (PrivacyType) privacyItemsChip.mConfig.privacyTypes.get(i2);
                                ImageView imageView = (ImageView) ((ArrayList) privacyItemsChip.mIcons).get(i2);
                                boolean contains = set.contains(privacyType);
                                z = z || contains;
                                if (contains) {
                                    i3 = 0;
                                }
                                imageView.setVisibility(i3);
                                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                                int i5 = privacyItemsChip.mIconSize;
                                layoutParams.width = i5;
                                layoutParams.height = i5;
                                imageView.requestLayout();
                                i2++;
                            }
                        }
                        break;
                }
            }
        });
        TransitionManager.beginDelayedTransition(this.mChipsContainer, this.mTransition);
        final int i2 = 1;
        ((ArrayList) this.mChips).forEach(new Consumer() { // from class: com.android.systemui.privacy.television.TvPrivacyChipsController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        arraySet.add(((PrivacyItem) obj).privacyType);
                        break;
                    default:
                        Set set = arraySet;
                        PrivacyItemsChip privacyItemsChip = (PrivacyItemsChip) obj;
                        int i22 = 0;
                        boolean z = false;
                        while (true) {
                            int i3 = 8;
                            if (i22 >= privacyItemsChip.mConfig.privacyTypes.size()) {
                                if (!z) {
                                    privacyItemsChip.setVisibility(8);
                                    if (privacyItemsChip.mState != 0) {
                                        privacyItemsChip.mState = 0;
                                        break;
                                    }
                                } else {
                                    int i4 = privacyItemsChip.mState;
                                    if (i4 == 0) {
                                        PrivacyChipDrawable privacyChipDrawable = privacyItemsChip.mChipBackgroundDrawable;
                                        if (!privacyChipDrawable.mIsExpanded) {
                                            privacyChipDrawable.mIsExpanded = true;
                                            privacyChipDrawable.mCollapseProgress = 0.0f;
                                            privacyChipDrawable.invalidateSelf();
                                        }
                                    } else if (i4 == 2) {
                                        PrivacyChipDrawable privacyChipDrawable2 = privacyItemsChip.mChipBackgroundDrawable;
                                        if (!privacyChipDrawable2.mIsExpanded) {
                                            privacyChipDrawable2.mIsExpanded = true;
                                            privacyChipDrawable2.mCollapse.cancel();
                                            privacyChipDrawable2.mExpand.start();
                                        }
                                    }
                                    privacyItemsChip.setVisibility(0);
                                    if (privacyItemsChip.mState != 1) {
                                        privacyItemsChip.mState = 1;
                                        break;
                                    }
                                }
                            } else {
                                PrivacyType privacyType = (PrivacyType) privacyItemsChip.mConfig.privacyTypes.get(i22);
                                ImageView imageView = (ImageView) ((ArrayList) privacyItemsChip.mIcons).get(i22);
                                boolean contains = set.contains(privacyType);
                                z = z || contains;
                                if (contains) {
                                    i3 = 0;
                                }
                                imageView.setVisibility(i3);
                                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                                int i5 = privacyItemsChip.mIconSize;
                                layoutParams.width = i5;
                                layoutParams.height = i5;
                                imageView.requestLayout();
                                i22++;
                            }
                        }
                        break;
                }
            }
        });
    }

    public final void updateStaticPrivacyIndicatorBounds() {
        Context context = this.mContext;
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.privacy_chips_max_width);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.privacy_chip_height);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.privacy_chip_margin) * 2;
        Rect bounds = ((WindowManager) context.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
        boolean z = this.mIsRtl;
        int i = z ? bounds.left : bounds.right - dimensionPixelSize;
        int i2 = bounds.top;
        Rect rect = new Rect(i, i2, z ? bounds.left + dimensionPixelSize : bounds.right, dimensionPixelSize3 + i2 + dimensionPixelSize2);
        Rect[] rectArr = this.mBounds;
        rectArr[0] = rect;
        try {
            this.mIWindowManager.updateStaticPrivacyIndicatorBounds(context.getDisplayId(), rectArr);
        } catch (RemoteException unused) {
            Log.w("TvPrivacyChipsController", "could not update privacy indicator bounds");
        }
    }
}
