package com.android.systemui.statusbar.policy;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.ColorUtils;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardVisibilityHelper;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class KeyguardUserSwitcherController extends ViewController implements DesktopManager.Callback {
    public static final AnimationProperties ANIMATION_PROPERTIES;
    public final KeyguardUserAdapter mAdapter;
    public final KeyguardUserSwitcherScrim mBackground;
    public int mBarState;
    public ObjectAnimator mBgAnimator;
    public float mDarkAmount;
    public final AnonymousClass4 mDataSetObserver;
    public int mDynamicLockMode;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public boolean mIsDexModeEnabled;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    public KeyguardUserSwitcherListView mListView;
    public final ScreenLifecycle mScreenLifecycle;
    public final AnonymousClass2 mScreenObserver;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final AnonymousClass3 mStatusBarStateListener;
    public final UserSwitcherController mUserSwitcherController;
    public boolean mUserSwitcherOpen;

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360L;
        ANIMATION_PROPERTIES = animationProperties;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.policy.KeyguardUserSwitcherController$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.policy.KeyguardUserSwitcherController$3] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.policy.KeyguardUserSwitcherController$4] */
    public KeyguardUserSwitcherController(KeyguardUserSwitcherView keyguardUserSwitcherView, Context context, Resources resources, LayoutInflater layoutInflater, ScreenLifecycle screenLifecycle, UserSwitcherController userSwitcherController, KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, PowerManager powerManager) {
        super(keyguardUserSwitcherView);
        this.mIsDexModeEnabled = false;
        this.mDynamicLockMode = 0;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDlsViewModeChanged(int i) {
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                keyguardUserSwitcherController.mDynamicLockMode = i;
                keyguardUserSwitcherController.updatevisibility();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onKeyguardVisibilityChanged ", "KeyguardUserSwitcherController", z);
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                if (!z) {
                    keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
                }
                if (LsRune.LOCKUI_MULTI_USER) {
                    keyguardUserSwitcherController.mAdapter.refreshUserOrder();
                    keyguardUserSwitcherController.updatevisibility();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                boolean z = LsRune.LOCKUI_MULTI_USER;
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                if (z) {
                    keyguardUserSwitcherController.updatevisibility();
                }
                keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
            }
        };
        this.mScreenObserver = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController.2
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOff() {
                Log.d("KeyguardUserSwitcherController", "onScreenTurnedOff");
                KeyguardUserSwitcherController.this.closeSwitcherIfOpenAndNotSimple(false);
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController.3
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                Log.d("KeyguardUserSwitcherController", String.format("onDozeAmountChanged: linearAmount=%f amount=%f", Float.valueOf(f), Float.valueOf(f2)));
                AnimationProperties animationProperties = KeyguardUserSwitcherController.ANIMATION_PROPERTIES;
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                keyguardUserSwitcherController.getClass();
                boolean z = f2 == 1.0f;
                if (f2 == keyguardUserSwitcherController.mDarkAmount) {
                    return;
                }
                keyguardUserSwitcherController.mDarkAmount = f2;
                KeyguardUserSwitcherListView keyguardUserSwitcherListView = keyguardUserSwitcherController.mListView;
                int childCount = keyguardUserSwitcherListView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = keyguardUserSwitcherListView.getChildAt(i);
                    if (childAt instanceof KeyguardUserDetailItemView) {
                        KeyguardUserDetailItemView keyguardUserDetailItemView = (KeyguardUserDetailItemView) childAt;
                        if (keyguardUserDetailItemView.mDarkAmount != f2) {
                            keyguardUserDetailItemView.mDarkAmount = f2;
                            keyguardUserDetailItemView.mName.setTextColor(ColorUtils.blendARGB(f2, keyguardUserDetailItemView.mTextColor, -1));
                        }
                    }
                }
                if (z) {
                    keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                Log.d("KeyguardUserSwitcherController", String.format("onStateChanged: newState=%d", Integer.valueOf(i)));
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                boolean goingToFullShade = ((StatusBarStateControllerImpl) keyguardUserSwitcherController.mStatusBarStateController).goingToFullShade();
                KeyguardStateController keyguardStateController2 = keyguardUserSwitcherController.mKeyguardStateController;
                boolean z = ((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardFadingAway;
                int i2 = keyguardUserSwitcherController.mBarState;
                keyguardUserSwitcherController.mBarState = i;
                if (((StatusBarStateControllerImpl) keyguardUserSwitcherController.mStatusBarStateController).goingToFullShade() || ((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardFadingAway) {
                    keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
                }
                keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i, i2, z, goingToFullShade);
            }
        };
        this.mDataSetObserver = new DataSetObserver() { // from class: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController.4
            @Override // android.database.DataSetObserver
            public final void onChanged() {
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                int childCount = keyguardUserSwitcherController.mListView.getChildCount();
                KeyguardUserAdapter keyguardUserAdapter = keyguardUserSwitcherController.mAdapter;
                int count = keyguardUserAdapter.getCount();
                int max = Math.max(childCount, count);
                Log.d("KeyguardUserSwitcherController", String.format("refreshUserList childCount=%d adapterCount=%d", Integer.valueOf(childCount), Integer.valueOf(count)));
                int i = 0;
                boolean z = false;
                while (i < max) {
                    if (i < count) {
                        View childAt = i < childCount ? keyguardUserSwitcherController.mListView.getChildAt(i) : null;
                        KeyguardUserDetailItemView keyguardUserDetailItemView = (KeyguardUserDetailItemView) keyguardUserAdapter.getView(i, childAt, keyguardUserSwitcherController.mListView);
                        UserRecord userRecord = (UserRecord) keyguardUserDetailItemView.getTag();
                        if (userRecord.isCurrent) {
                            if (i != 0) {
                                Log.w("KeyguardUserSwitcherController", "Current user is not the first view in the list");
                            }
                            int i2 = userRecord.info.id;
                            keyguardUserDetailItemView.updateVisibilities(true, keyguardUserSwitcherController.mUserSwitcherOpen, false);
                            z = true;
                        } else {
                            keyguardUserDetailItemView.updateVisibilities(keyguardUserSwitcherController.mUserSwitcherOpen, true, false);
                        }
                        float f = keyguardUserSwitcherController.mDarkAmount;
                        if (keyguardUserDetailItemView.mDarkAmount != f) {
                            keyguardUserDetailItemView.mDarkAmount = f;
                            keyguardUserDetailItemView.mName.setTextColor(ColorUtils.blendARGB(f, keyguardUserDetailItemView.mTextColor, -1));
                        }
                        if (childAt == null) {
                            keyguardUserSwitcherController.mListView.addView(keyguardUserDetailItemView);
                        } else if (childAt != keyguardUserDetailItemView) {
                            KeyguardUserSwitcherListView keyguardUserSwitcherListView = keyguardUserSwitcherController.mListView;
                            keyguardUserSwitcherListView.removeViewAt(i);
                            keyguardUserSwitcherListView.addView(keyguardUserDetailItemView, i);
                        }
                    } else {
                        KeyguardUserSwitcherListView keyguardUserSwitcherListView2 = keyguardUserSwitcherController.mListView;
                        keyguardUserSwitcherListView2.removeViewAt(keyguardUserSwitcherListView2.getChildCount() - 1);
                    }
                    i++;
                }
                if (z) {
                    return;
                }
                Log.w("KeyguardUserSwitcherController", "Current user is not listed");
            }
        };
        Log.d("KeyguardUserSwitcherController", "New KeyguardUserSwitcherController");
        this.mScreenLifecycle = screenLifecycle;
        this.mUserSwitcherController = userSwitcherController;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAdapter = LsRune.LOCKUI_MULTI_USER ? new KeyguardUserAdapter(context, resources, layoutInflater, userSwitcherController, powerManager, this) : new KeyguardUserAdapter(context, resources, layoutInflater, userSwitcherController, this);
        this.mKeyguardVisibilityHelper = new KeyguardVisibilityHelper(this.mView, keyguardStateController, dozeParameters, screenOffAnimationController, false, null);
        this.mBackground = new KeyguardUserSwitcherScrim(context);
    }

    public final boolean closeSwitcherIfOpenAndNotSimple(boolean z) {
        if (!this.mUserSwitcherOpen || ((UserSwitcherSettingsModel) ((UserRepositoryImpl) this.mUserSwitcherController.getMUserSwitcherInteractor().repository)._userSwitcherSettings.$$delegate_0.getValue()).isSimpleUserSwitcher) {
            return false;
        }
        setUserSwitcherOpened(false, z);
        return true;
    }

    @Override // com.android.systemui.util.DesktopManager.Callback
    public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        this.mIsDexModeEnabled = semDesktopModeState != null && (semDesktopModeState.getEnabled() == 3 || semDesktopModeState.getEnabled() == 4);
        updatevisibility();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        Log.d("KeyguardUserSwitcherController", "onInit");
        this.mListView = (KeyguardUserSwitcherListView) ((KeyguardUserSwitcherView) this.mView).findViewById(R.id.keyguard_user_switcher_list);
        if (LsRune.LOCKUI_MULTI_USER) {
            updatevisibility();
        }
        ((KeyguardUserSwitcherView) this.mView).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
                if (keyguardUserSwitcherController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating || keyguardUserSwitcherController.mListView.mAnimating) {
                    return false;
                }
                return keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Log.d("KeyguardUserSwitcherController", "onViewAttached");
        KeyguardUserAdapter keyguardUserAdapter = this.mAdapter;
        keyguardUserAdapter.registerDataSetObserver(this.mDataSetObserver);
        keyguardUserAdapter.notifyDataSetChanged();
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).addCallback((StatusBarStateController.StateListener) this.mStatusBarStateListener);
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        if (LsRune.LOCKUI_MULTI_USER) {
            ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).registerCallback(this);
            onDesktopModeStateChanged(((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).getSemDesktopModeState());
        }
        if (((UserSwitcherSettingsModel) ((UserRepositoryImpl) this.mUserSwitcherController.getMUserSwitcherInteractor().repository)._userSwitcherSettings.$$delegate_0.getValue()).isSimpleUserSwitcher) {
            setUserSwitcherOpened(true, true);
            return;
        }
        KeyguardUserSwitcherView keyguardUserSwitcherView = (KeyguardUserSwitcherView) this.mView;
        KeyguardUserSwitcherScrim keyguardUserSwitcherScrim = this.mBackground;
        keyguardUserSwitcherView.addOnLayoutChangeListener(keyguardUserSwitcherScrim);
        ((KeyguardUserSwitcherView) this.mView).setBackground(keyguardUserSwitcherScrim);
        keyguardUserSwitcherScrim.setAlpha(0);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Log.d("KeyguardUserSwitcherController", "onViewDetached");
        closeSwitcherIfOpenAndNotSimple(false);
        this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this.mStatusBarStateListener);
        this.mScreenLifecycle.removeObserver(this.mScreenObserver);
        KeyguardUserSwitcherView keyguardUserSwitcherView = (KeyguardUserSwitcherView) this.mView;
        KeyguardUserSwitcherScrim keyguardUserSwitcherScrim = this.mBackground;
        keyguardUserSwitcherView.removeOnLayoutChangeListener(keyguardUserSwitcherScrim);
        ((KeyguardUserSwitcherView) this.mView).setBackground(null);
        keyguardUserSwitcherScrim.setAlpha(0);
        if (LsRune.LOCKUI_MULTI_USER) {
            ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).unregisterCallback(this);
        }
    }

    public final void setAlpha(float f) {
        if (this.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
            return;
        }
        ((KeyguardUserSwitcherView) this.mView).setAlpha(f);
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException
        */
    public final void setUserSwitcherOpened(boolean r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController.setUserSwitcherOpened(boolean, boolean):void");
    }

    public final void updatePosition(int i, int i2, boolean z) {
        AnimationProperties animationProperties = ANIMATION_PROPERTIES;
        PropertyAnimator.setProperty(this.mListView, AnimatableProperty.Y, i2, animationProperties, z);
        PropertyAnimator.setProperty(this.mListView, AnimatableProperty.TRANSLATION_X, -Math.abs(i), animationProperties, z);
        Rect rect = new Rect();
        this.mListView.getDrawingRect(rect);
        ((KeyguardUserSwitcherView) this.mView).offsetDescendantRectToMyCoords(this.mListView, rect);
        int translationX = (int) (this.mListView.getTranslationX() + rect.left + (rect.width() / 2));
        int translationY = (int) (this.mListView.getTranslationY() + rect.top + (rect.height() / 2));
        KeyguardUserSwitcherScrim keyguardUserSwitcherScrim = this.mBackground;
        keyguardUserSwitcherScrim.mCircleX = translationX;
        keyguardUserSwitcherScrim.mCircleY = translationY;
        keyguardUserSwitcherScrim.updatePaint();
    }

    public final void updatevisibility() {
        int i;
        KeyguardUserSwitcherListView keyguardUserSwitcherListView = this.mListView;
        if (LsRune.LOCKUI_MULTI_USER && LsRune.KEYGUARD_FBE) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (!keyguardUpdateMonitor.isKidsModeRunning() && !this.mIsDexModeEnabled && keyguardUpdateMonitor.isUserUnlocked() && this.mDynamicLockMode == 0 && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isUserSwitcherSettingOn() && this.mAdapter.getCount() > 1 && keyguardUpdateMonitor.isKeyguardVisible()) {
                i = 0;
                keyguardUserSwitcherListView.setVisibility(i);
            }
        }
        i = 8;
        keyguardUserSwitcherListView.setVisibility(i);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class KeyguardUserAdapter extends BaseUserSwitcherAdapter implements View.OnClickListener {
        public final Context mContext;
        public final KeyguardUserSwitcherController mKeyguardUserSwitcherController;
        public final LayoutInflater mLayoutInflater;
        public final PowerManager mPowerManager;
        public final Resources mResources;
        public ArrayList mUsersOrdered;

        public KeyguardUserAdapter(Context context, Resources resources, LayoutInflater layoutInflater, UserSwitcherController userSwitcherController, PowerManager powerManager, KeyguardUserSwitcherController keyguardUserSwitcherController) {
            this(context, resources, layoutInflater, userSwitcherController, keyguardUserSwitcherController);
            this.mPowerManager = powerManager;
        }

        public final Drawable getDrawable(UserRecord userRecord) {
            Drawable drawable = (userRecord.isCurrent && userRecord.isGuest) ? this.mContext.getDrawable(R.drawable.ic_avatar_guest_user) : BaseUserSwitcherAdapter.getIconDrawable(this.mContext, userRecord);
            drawable.setTint(this.mResources.getColor(userRecord.isSwitchToEnabled ? R.color.kg_user_switcher_avatar_icon_color : R.color.kg_user_switcher_restricted_avatar_icon_color, this.mContext.getTheme()));
            return new LayerDrawable(new Drawable[]{this.mContext.getDrawable(R.drawable.user_avatar_bg), drawable});
        }

        @Override // com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter
        public final List getUsers() {
            return this.mUsersOrdered;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            ColorFilter colorFilter;
            Bitmap bitmap;
            UserRecord item = getItem(i);
            if (!(view instanceof KeyguardUserDetailItemView) || !(view.getTag() instanceof UserRecord)) {
                view = this.mLayoutInflater.inflate(R.layout.keyguard_user_switcher_item, viewGroup, false);
            }
            KeyguardUserDetailItemView keyguardUserDetailItemView = (KeyguardUserDetailItemView) view;
            keyguardUserDetailItemView.setOnClickListener(this);
            String name = getName(this.mContext, item);
            Bitmap bitmap2 = item.picture;
            boolean z = item.isSwitchToEnabled;
            if (bitmap2 != null) {
                CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(item.picture, (int) this.mResources.getDimension(R.dimen.kg_framed_avatar_size));
                if (z) {
                    colorFilter = null;
                } else {
                    BaseUserSwitcherAdapter.Companion.getClass();
                    colorFilter = (ColorFilter) BaseUserSwitcherAdapter.disabledUserAvatarColorFilter$delegate.getValue();
                }
                circleFramedDrawable.setColorFilter(colorFilter);
                keyguardUserDetailItemView.bind(name, circleFramedDrawable, item.info.id);
            } else if (LsRune.LOCKUI_MULTI_USER) {
                Drawable mutate = getDrawable(item).mutate();
                int dimension = (int) this.mResources.getDimension(R.dimen.kg_framed_avatar_size);
                AnimationProperties animationProperties = KeyguardUserSwitcherController.ANIMATION_PROPERTIES;
                if (mutate instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) mutate;
                    if (bitmapDrawable.getBitmap() != null) {
                        bitmap = bitmapDrawable.getBitmap();
                        keyguardUserDetailItemView.bind(name, new CircleFramedDrawable(bitmap, dimension), item.resolveId());
                    }
                }
                Bitmap createBitmap = (mutate.getIntrinsicWidth() <= 0 || mutate.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(mutate.getIntrinsicWidth(), mutate.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                mutate.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                mutate.draw(canvas);
                bitmap = createBitmap;
                keyguardUserDetailItemView.bind(name, new CircleFramedDrawable(bitmap, dimension), item.resolveId());
            } else {
                keyguardUserDetailItemView.bind(name, getDrawable(item).mutate(), item.resolveId());
            }
            keyguardUserDetailItemView.setActivated(item.isCurrent);
            boolean z2 = item.enforcedAdmin != null;
            View view2 = keyguardUserDetailItemView.mRestrictedPadlock;
            if (view2 != null) {
                view2.setVisibility(z2 ? 0 : 8);
            }
            keyguardUserDetailItemView.setEnabled(!z2);
            keyguardUserDetailItemView.setEnabled(z);
            UserSwitcherController.setSelectableAlpha(keyguardUserDetailItemView);
            keyguardUserDetailItemView.setTag(item);
            return keyguardUserDetailItemView;
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetChanged() {
            refreshUserOrder();
            super.notifyDataSetChanged();
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            PowerManager powerManager;
            UserRecord userRecord = (UserRecord) view.getTag();
            if (LsRune.LOCKUI_MULTI_USER && (powerManager = this.mPowerManager) != null) {
                powerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
            }
            KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
            AnimationProperties animationProperties = KeyguardUserSwitcherController.ANIMATION_PROPERTIES;
            if (keyguardUserSwitcherController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating || keyguardUserSwitcherController.mListView.mAnimating) {
                return;
            }
            if (!keyguardUserSwitcherController.mUserSwitcherOpen) {
                keyguardUserSwitcherController.setUserSwitcherOpened(true, true);
            } else if (!userRecord.isCurrent || userRecord.isGuest) {
                onUserListItemClicked(userRecord, null);
            } else {
                keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
            }
        }

        public final void refreshUserOrder() {
            ArrayList arrayList = (ArrayList) super.getUsers();
            this.mUsersOrdered = new ArrayList(arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                UserRecord userRecord = (UserRecord) arrayList.get(i);
                if (userRecord.isCurrent) {
                    this.mUsersOrdered.add(0, userRecord);
                } else {
                    this.mUsersOrdered.add(userRecord);
                }
            }
        }

        public KeyguardUserAdapter(Context context, Resources resources, LayoutInflater layoutInflater, UserSwitcherController userSwitcherController, KeyguardUserSwitcherController keyguardUserSwitcherController) {
            super(userSwitcherController);
            this.mUsersOrdered = new ArrayList();
            this.mContext = context;
            this.mResources = resources;
            this.mLayoutInflater = layoutInflater;
            this.mKeyguardUserSwitcherController = keyguardUserSwitcherController;
        }
    }
}
