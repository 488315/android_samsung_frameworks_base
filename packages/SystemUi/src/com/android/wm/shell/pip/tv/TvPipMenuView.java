package com.android.wm.shell.pip.tv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.widget.LinearLayoutManager;
import com.android.internal.widget.RecyclerView;
import com.android.wm.shell.common.TvWindowMenuActionButton;
import com.android.wm.shell.pip.tv.TvPipActionsProvider;
import com.android.wm.shell.pip.tv.TvPipMenuEduTextDrawer;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipMenuView extends FrameLayout implements TvPipActionsProvider.Listener, TvPipMenuEduTextDrawer.Listener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final TvWindowMenuActionButton mA11yDoneButton;
    public final AccessibilityManager mA11yManager;
    public final RecyclerView mActionButtonsRecyclerView;
    public final ImageView mArrowDown;
    public final int mArrowElevation;
    public final ImageView mArrowLeft;
    public final ImageView mArrowRight;
    public final ImageView mArrowUp;
    public final LinearLayoutManager mButtonLayoutManager;
    public int mCurrentMenuMode;
    public final Rect mCurrentPipBounds;
    public int mCurrentPipGravity;
    public final View mDimLayer;
    public final ViewGroup mEduTextContainer;
    public final TvPipMenuEduTextDrawer mEduTextDrawer;
    public final Listener mListener;
    public final Handler mMainHandler;
    public final View mMenuFrameView;
    public final View mPipBackground;
    public final View mPipFrameView;
    public final int mPipMenuBorderWidth;
    public final int mPipMenuFadeAnimationDuration;
    public final int mPipMenuOuterSpace;
    public final View mPipView;
    public final RecyclerViewAdapter mRecyclerViewAdapter;
    public final int mResizeAnimationDuration;
    public final TvPipActionsProvider mTvPipActionsProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecyclerViewAdapter extends RecyclerView.Adapter {
        public final List mActionList;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final TvWindowMenuActionButton mButton;

            public ButtonViewHolder(View view) {
                super(view);
                TvWindowMenuActionButton tvWindowMenuActionButton = (TvWindowMenuActionButton) view;
                this.mButton = tvWindowMenuActionButton;
                tvWindowMenuActionButton.setOnClickListener(this);
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecyclerViewAdapter recyclerViewAdapter = RecyclerViewAdapter.this;
                TvPipAction tvPipAction = (TvPipAction) recyclerViewAdapter.mActionList.get(TvPipMenuView.this.mActionButtonsRecyclerView.getChildLayoutPosition(view));
                if (tvPipAction != null) {
                    tvPipAction.executeAction();
                }
            }
        }

        public RecyclerViewAdapter(List<TvPipAction> list) {
            this.mActionList = list;
        }

        public final int getItemCount() {
            return this.mActionList.size();
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ((TvPipAction) this.mActionList.get(i)).populateButton(((ButtonViewHolder) viewHolder).mButton, TvPipMenuView.this.mMainHandler);
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ButtonViewHolder(new TvWindowMenuActionButton(((FrameLayout) TvPipMenuView.this).mContext));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v17, types: [com.android.wm.shell.pip.tv.TvPipMenuView$1] */
    public TvPipMenuView(Context context, Handler handler, Listener listener, TvPipActionsProvider tvPipActionsProvider) {
        super(context, null, 0, 0);
        this.mCurrentMenuMode = 0;
        this.mCurrentPipBounds = new Rect();
        FrameLayout.inflate(context, R.layout.tv_pip_menu, this);
        this.mMainHandler = handler;
        this.mListener = listener;
        this.mA11yManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        RecyclerView findViewById = findViewById(R.id.tv_pip_menu_action_buttons);
        this.mActionButtonsRecyclerView = findViewById;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(((FrameLayout) this).mContext);
        this.mButtonLayoutManager = linearLayoutManager;
        findViewById.setLayoutManager(linearLayoutManager);
        findViewById.setPreserveFocusAfterLayout(true);
        this.mTvPipActionsProvider = tvPipActionsProvider;
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(tvPipActionsProvider.mActionsList);
        this.mRecyclerViewAdapter = recyclerViewAdapter;
        findViewById.setAdapter(recyclerViewAdapter);
        ArrayList arrayList = (ArrayList) tvPipActionsProvider.mListeners;
        if (!arrayList.contains(this)) {
            arrayList.add(this);
        }
        this.mMenuFrameView = findViewById(R.id.tv_pip_menu_frame);
        this.mPipFrameView = findViewById(R.id.tv_pip_border);
        this.mPipView = findViewById(R.id.tv_pip);
        this.mPipBackground = findViewById(R.id.tv_pip_menu_background);
        this.mDimLayer = findViewById(R.id.tv_pip_menu_dim_layer);
        ImageView imageView = (ImageView) findViewById(R.id.tv_pip_menu_arrow_up);
        this.mArrowUp = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id.tv_pip_menu_arrow_right);
        this.mArrowRight = imageView2;
        ImageView imageView3 = (ImageView) findViewById(R.id.tv_pip_menu_arrow_down);
        this.mArrowDown = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R.id.tv_pip_menu_arrow_left);
        this.mArrowLeft = imageView4;
        this.mA11yDoneButton = (TvWindowMenuActionButton) findViewById(R.id.tv_pip_menu_done_button);
        Resources resources = context.getResources();
        this.mResizeAnimationDuration = resources.getInteger(R.integer.config_pipResizeAnimationDuration);
        this.mPipMenuFadeAnimationDuration = resources.getInteger(R.integer.tv_window_menu_fade_animation_duration);
        this.mPipMenuOuterSpace = resources.getDimensionPixelSize(R.dimen.pip_menu_outer_space);
        this.mPipMenuBorderWidth = resources.getDimensionPixelSize(R.dimen.pip_menu_border_width);
        this.mArrowElevation = resources.getDimensionPixelSize(R.dimen.pip_menu_arrow_elevation);
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.pip_menu_arrow_size);
        Path path = new Path();
        float f = dimensionPixelSize;
        path.lineTo(0.0f, f);
        float f2 = dimensionPixelSize / 2;
        path.lineTo(f2, f2);
        path.close();
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(new PathShape(path, f, f));
        shapeDrawable.setTint(((FrameLayout) this).mContext.getResources().getColor(R.color.tv_pip_menu_arrow_color));
        ?? r3 = new ViewOutlineProvider() { // from class: com.android.wm.shell.pip.tv.TvPipMenuView.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                TvPipMenuView tvPipMenuView = TvPipMenuView.this;
                int measuredHeight = view.getMeasuredHeight();
                int i = TvPipMenuView.$r8$clinit;
                tvPipMenuView.getClass();
                Path path2 = new Path();
                path2.lineTo(0.0f, measuredHeight);
                float f3 = measuredHeight / 2;
                path2.lineTo(f3, f3);
                path2.close();
                outline.setPath(path2);
            }
        };
        initArrow(imageView2, r3, shapeDrawable, 0);
        initArrow(imageView3, r3, shapeDrawable, 90);
        initArrow(imageView4, r3, shapeDrawable, 180);
        initArrow(imageView, r3, shapeDrawable, 270);
        TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer = new TvPipMenuEduTextDrawer(((FrameLayout) this).mContext, handler, this);
        this.mEduTextDrawer = tvPipMenuEduTextDrawer;
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.tv_pip_menu_edu_text_container);
        this.mEduTextContainer = viewGroup;
        viewGroup.addView(tvPipMenuEduTextDrawer);
    }

    public final void animateAlphaTo(final View view, final float f) {
        if (view.getAlpha() == f) {
            return;
        }
        final int i = 0;
        ViewPropertyAnimator withStartAction = view.animate().alpha(f).setInterpolator(f == 0.0f ? TvPipInterpolators.EXIT : TvPipInterpolators.ENTER).setDuration(this.mPipMenuFadeAnimationDuration).withStartAction(new Runnable() { // from class: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        float f2 = f;
                        View view2 = view;
                        if (f2 != 0.0f) {
                            view2.setVisibility(0);
                            break;
                        }
                        break;
                    default:
                        float f3 = f;
                        View view3 = view;
                        if (f3 == 0.0f) {
                            view3.setVisibility(8);
                            break;
                        }
                        break;
                }
            }
        });
        final int i2 = 1;
        withStartAction.withEndAction(new Runnable() { // from class: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        float f2 = f;
                        View view2 = view;
                        if (f2 != 0.0f) {
                            view2.setVisibility(0);
                            break;
                        }
                        break;
                    default:
                        float f3 = f;
                        View view3 = view;
                        if (f3 == 0.0f) {
                            view3.setVisibility(8);
                            break;
                        }
                        break;
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            if (keyEvent.getKeyCode() == 4) {
                TvPipMenuController tvPipMenuController = (TvPipMenuController) this.mListener;
                if (!tvPipMenuController.onExitMoveMode()) {
                    tvPipMenuController.closeMenu();
                }
                return true;
            }
            if (this.mA11yManager.isEnabled()) {
                return super.dispatchKeyEvent(keyEvent);
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 66) {
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        return ((TvPipMenuController) this.mListener).onPipMovement(keyEvent.getKeyCode()) || super.dispatchKeyEvent(keyEvent);
                }
            }
            return ((TvPipMenuController) this.mListener).onExitMoveMode() || super.dispatchKeyEvent(keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void hideMovementHints() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1433716912, 0, "%s: hideMovementHints()", "TvPipMenuView");
        }
        if (this.mCurrentMenuMode != 1) {
            return;
        }
        animateAlphaTo(this.mArrowUp, 0.0f);
        animateAlphaTo(this.mArrowRight, 0.0f);
        animateAlphaTo(this.mArrowDown, 0.0f);
        animateAlphaTo(this.mArrowLeft, 0.0f);
        animateAlphaTo(this.mA11yDoneButton, 0.0f);
    }

    public final void initArrow(View view, C40781 c40781, Drawable drawable, int i) {
        view.setOutlineProvider(c40781);
        view.setBackground(drawable);
        view.setRotation(i);
        view.setElevation(this.mArrowElevation);
    }

    @Override // com.android.wm.shell.pip.tv.TvPipActionsProvider.Listener
    public final void onActionsChanged(int i, int i2, int i3) {
        this.mRecyclerViewAdapter.notifyItemRangeChanged(i3, i2);
        if (i > 0) {
            this.mRecyclerViewAdapter.notifyItemRangeInserted(i3 + i2, i);
        } else if (i < 0) {
            this.mRecyclerViewAdapter.notifyItemRangeRemoved(i3 + i2, -i);
        }
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        TvPipMenuController tvPipMenuController = (TvPipMenuController) this.mListener;
        tvPipMenuController.getClass();
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 276313851, 12, "%s: onPipWindowFocusChanged - focused=%b", "TvPipMenuController", Boolean.valueOf(z));
        }
        tvPipMenuController.mMenuIsFocused = z;
        if (z || !tvPipMenuController.isMenuOpen()) {
            return;
        }
        tvPipMenuController.closeMenu();
    }

    public final void refocusButton(int i) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 559199821, 4, "%s: refocusButton, position: %d", "TvPipMenuView", Long.valueOf(i));
        }
        View findViewByPosition = this.mButtonLayoutManager.findViewByPosition(i);
        if (findViewByPosition != null) {
            findViewByPosition.requestFocus();
            findViewByPosition.requestAccessibilityFocus();
        }
    }

    public final void setArrowA11yEnabled(View view, final int i, boolean z) {
        view.setClickable(z);
        if (z) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    TvPipMenuView tvPipMenuView = TvPipMenuView.this;
                    ((TvPipMenuController) tvPipMenuView.mListener).onPipMovement(i);
                }
            });
        }
    }

    public final void setMenuButtonsVisible(boolean z) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2089901900, 12, "%s: showUserActions: %b", "TvPipMenuView", Boolean.valueOf(z));
        }
        animateAlphaTo(this.mActionButtonsRecyclerView, z ? 1.0f : 0.0f);
    }

    public final void showMovementHints() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1380842406, 0, "%s: showMovementHints(), position: %s", "TvPipMenuView", String.valueOf(Gravity.toString(this.mCurrentPipGravity)));
        }
        animateAlphaTo(this.mArrowUp, (this.mCurrentPipGravity & 80) == 80 ? 1.0f : 0.0f);
        animateAlphaTo(this.mArrowDown, (this.mCurrentPipGravity & 48) == 48 ? 1.0f : 0.0f);
        animateAlphaTo(this.mArrowLeft, (this.mCurrentPipGravity & 5) == 5 ? 1.0f : 0.0f);
        animateAlphaTo(this.mArrowRight, (this.mCurrentPipGravity & 3) == 3 ? 1.0f : 0.0f);
        boolean isEnabled = this.mA11yManager.isEnabled();
        setArrowA11yEnabled(this.mArrowUp, 19, isEnabled);
        setArrowA11yEnabled(this.mArrowDown, 20, isEnabled);
        setArrowA11yEnabled(this.mArrowLeft, 21, isEnabled);
        setArrowA11yEnabled(this.mArrowRight, 22, isEnabled);
        animateAlphaTo(this.mA11yDoneButton, isEnabled ? 1.0f : 0.0f);
        if (isEnabled) {
            this.mA11yDoneButton.setVisibility(0);
            this.mA11yDoneButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ((TvPipMenuController) TvPipMenuView.this.mListener).onExitMoveMode();
                }
            });
            this.mA11yDoneButton.requestFocus();
            this.mA11yDoneButton.requestAccessibilityFocus();
        }
    }
}
