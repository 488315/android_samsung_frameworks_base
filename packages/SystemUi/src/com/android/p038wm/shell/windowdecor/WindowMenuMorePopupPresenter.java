package com.android.p038wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.android.p038wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowMenuMorePopupPresenter extends WindowMenuPresenter {
    public final ViewGroup[] mButtonSet;
    public final WindowMenuCaptionPresenter mCaptionMenuPresenter;
    public int mFirstButtonId;
    public int mLastButtonId;
    public final SparseArray mMoreButtons;

    static {
        boolean z = CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE;
    }

    public WindowMenuMorePopupPresenter(Context context, ActivityManager.RunningTaskInfo runningTaskInfo, View view, MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener, float f, int i, WindowMenuCaptionPresenter windowMenuCaptionPresenter, boolean z) {
        super(context, runningTaskInfo, runningTaskInfo.getWindowingMode(), captionTouchEventListener, view, f, z);
        MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener2;
        boolean z2;
        WindowMenuCaptionPresenter windowMenuCaptionPresenter2;
        SparseArray sparseArray;
        ViewGroup viewGroup;
        this.mMoreButtons = new SparseArray();
        ((GradientDrawable) view.getBackground()).setColor(i);
        this.mCaptionMenuPresenter = windowMenuCaptionPresenter;
        this.mButtonSet = new ViewGroup[]{(ViewGroup) view.findViewById(R.id.primary_button_set), (ViewGroup) view.findViewById(R.id.secondary_button_set)};
        this.mFirstButtonId = -1;
        this.mLastButtonId = -1;
        int i2 = 0;
        while (true) {
            ViewGroup[] viewGroupArr = this.mButtonSet;
            int length = viewGroupArr.length;
            captionTouchEventListener2 = this.mListener;
            z2 = this.mIsNewDexMode;
            windowMenuCaptionPresenter2 = this.mCaptionMenuPresenter;
            sparseArray = this.mMoreButtons;
            if (i2 >= length) {
                break;
            }
            ViewGroup viewGroup2 = viewGroupArr[i2];
            ViewGroup[] viewGroupArr2 = windowMenuCaptionPresenter2.mButtonSet;
            boolean z3 = viewGroupArr2.length > i2 && (viewGroup = viewGroupArr2[i2]) != null && viewGroup.getVisibility() == 0;
            if (viewGroup2 != null) {
                if (z3) {
                    viewGroup2.setVisibility(8);
                } else {
                    for (int i3 = 0; i3 < viewGroup2.getChildCount(); i3++) {
                        View childAt = viewGroup2.getChildAt(i3);
                        if (childAt instanceof WindowMenuDexItemView) {
                            WindowMenuDexItemView windowMenuDexItemView = (WindowMenuDexItemView) childAt;
                            WindowMenuItemView windowMenuItemView = (WindowMenuItemView) windowMenuCaptionPresenter2.mButtons.get(windowMenuDexItemView.getId());
                            if (windowMenuItemView != null) {
                                int visibility = windowMenuItemView.getVisibility();
                                windowMenuDexItemView.setVisibility(visibility);
                                if (visibility == 0) {
                                    sparseArray.put(windowMenuDexItemView.getId(), windowMenuDexItemView);
                                    windowMenuDexItemView.setEnabled(windowMenuItemView.isEnabled());
                                    windowMenuDexItemView.setOnClickListener(captionTouchEventListener2);
                                    if (this.mFirstButtonId == -1) {
                                        this.mFirstButtonId = windowMenuDexItemView.getId();
                                    }
                                    this.mLastButtonId = windowMenuDexItemView.getId();
                                    if (windowMenuDexItemView.getId() == R.id.window_pin_window) {
                                        int i4 = windowMenuItemView.mShowIconBackground ? R.string.sec_decor_button_text_window_unpin : R.string.sec_decor_button_text_window_pin;
                                        windowMenuDexItemView.setTextView(i4, this.mIsNightMode);
                                        windowMenuDexItemView.setContentDescription(this.mContext.getString(i4));
                                    } else {
                                        windowMenuDexItemView.setTextView(-1, this.mIsNightMode);
                                    }
                                }
                            } else if (z2 || windowMenuDexItemView.getId() != R.id.caption_unpin_window) {
                                windowMenuDexItemView.setVisibility(8);
                            } else if (windowMenuCaptionPresenter2.mUnpinButton != null) {
                                windowMenuDexItemView.setVisibility(0);
                                sparseArray.put(windowMenuDexItemView.getId(), windowMenuDexItemView);
                                windowMenuDexItemView.setOnClickListener(captionTouchEventListener2);
                                windowMenuDexItemView.setTextView(-1, this.mIsNightMode);
                            }
                        }
                    }
                }
            }
            i2++;
        }
        ViewGroup viewGroup3 = (ViewGroup) this.mRootView.findViewById(R.id.default_button_set);
        if (viewGroup3 != null) {
            for (int i5 = 0; i5 < viewGroup3.getChildCount(); i5++) {
                View childAt2 = viewGroup3.getChildAt(i5);
                if (childAt2 instanceof WindowMenuDexItemView) {
                    WindowMenuDexItemView windowMenuDexItemView2 = (WindowMenuDexItemView) childAt2;
                    boolean isButtonVisible = WindowMenuPresenter.isButtonVisible(windowMenuDexItemView2.getId(), this.mWindowingMode, false, this.mIsDisplayAdded);
                    windowMenuDexItemView2.setVisibility(isButtonVisible ? 0 : 8);
                    if (isButtonVisible) {
                        if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && z2 && windowMenuDexItemView2.getId() == R.id.caption_unpin_window) {
                            windowMenuDexItemView2.setVisibility(8);
                        } else {
                            if (CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY && this.mIsDisplayAdded && z2 && windowMenuDexItemView2.getId() == R.id.move_display_window) {
                                View view2 = windowMenuCaptionPresenter2.mMoveDisplayButtonSet;
                                if (view2 != null && view2.getVisibility() == 0) {
                                    windowMenuDexItemView2.setVisibility(8);
                                } else {
                                    windowMenuDexItemView2.setVisibility(0);
                                }
                            }
                            if (windowMenuDexItemView2.getId() == R.id.opacity_window) {
                                windowMenuCaptionPresenter2.setupOpacitySlider();
                            }
                            if (this.mFirstButtonId == -1) {
                                this.mFirstButtonId = windowMenuDexItemView2.getId();
                            }
                            this.mLastButtonId = windowMenuDexItemView2.getId();
                            sparseArray.put(windowMenuDexItemView2.getId(), windowMenuDexItemView2);
                            windowMenuDexItemView2.setOnClickListener(captionTouchEventListener2);
                            if (windowMenuDexItemView2.getId() == R.id.move_display_window) {
                                windowMenuDexItemView2.setTextView(this.mTaskInfo.getDisplayId() == 0 ? R.string.sec_decor_button_operation_move_display : R.string.sec_decor_button_operation_move_device, this.mIsNightMode);
                            } else {
                                windowMenuDexItemView2.setTextView(-1, this.mIsNightMode);
                            }
                        }
                    }
                }
            }
        }
        if (sparseArray.size() > 0) {
            ((WindowMenuDexItemView) sparseArray.get(this.mLastButtonId)).setVerticalPadding(false);
            ((WindowMenuDexItemView) sparseArray.get(this.mFirstButtonId)).setVerticalPadding(true);
        }
    }
}
