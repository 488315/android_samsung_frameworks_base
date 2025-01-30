package com.android.systemui.statusbar.policy;

import android.app.RemoteInput;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.UserHandle;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.ContentInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OnReceiveContentListener;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.ValueAnimator;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ContrastColorUtil;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.samsung.android.knox.accounts.Account;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RemoteInputView extends LinearLayout implements View.OnClickListener {
    public static final Object VIEW_TAG = new Object();
    public boolean mColorized;
    public GradientDrawable mContentBackground;
    public Rect mContentBackgroundBounds;
    public LinearLayout mContentView;
    public RemoteInputController mController;
    public ImageView mDelete;
    public RemoteEditText mEditText;
    public final ArrayList mEditTextFocusChangeListeners;
    public final EditorActionHandler mEditorActionHandler;
    public NotificationEntry mEntry;
    public boolean mIsAnimatingAppearance;
    public boolean mIsFocusAnimationFlagActive;
    public boolean mIsSms;
    public int mMaxLength;
    public final ArrayList mOnSendListeners;
    public final ArrayList mOnVisibilityChangedListeners;
    public String mPrevString;
    public ProgressBar mProgressBar;
    public boolean mResetting;
    public RevealParams mRevealParams;
    public TextView mSendButton;
    public boolean mSending;
    public String mSignature;
    public ViewRootImpl mTestableViewRootImpl;
    public Toast mTextLimitToast;
    public final SendButtonTextWatcher mTextWatcher;
    public int mTint;
    public final Object mToken;
    public final UiEventLogger mUiEventLogger;
    public RemoteInputViewController mViewController;
    public NotificationViewWrapper mWrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EditorActionHandler implements TextView.OnEditorActionListener {
        public /* synthetic */ EditorActionHandler(RemoteInputView remoteInputView, int i) {
            this();
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            boolean z = keyEvent == null && (i == 6 || i == 5 || i == 4);
            if (keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode())) {
                keyEvent.getAction();
            }
            if (!z) {
                return false;
            }
            if (RemoteInputView.this.mEditText.length() > 0 || RemoteInputView.this.mEntry.remoteInputAttachment != null) {
                RemoteInputView remoteInputView = RemoteInputView.this;
                remoteInputView.getClass();
                Iterator it = new ArrayList(remoteInputView.mOnSendListeners).iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
            return true;
        }

        private EditorActionHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum NotificationRemoteInputEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_REMOTE_INPUT_OPEN(795),
        NOTIFICATION_REMOTE_INPUT_CLOSE(796),
        NOTIFICATION_REMOTE_INPUT_SEND(797),
        NOTIFICATION_REMOTE_INPUT_FAILURE(798),
        NOTIFICATION_REMOTE_INPUT_ATTACH_IMAGE(825);

        private final int mId;

        NotificationRemoteInputEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class RemoteEditText extends EditText {
        public static final /* synthetic */ int $r8$clinit = 0;
        public InputMethodManager mInputMethodManager;
        public final LightBarController mLightBarController;
        public final RemoteInputView$RemoteEditText$$ExternalSyntheticLambda1 mOnBackInvokedCallback;
        public final RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0 mOnReceiveContentListener;
        public RemoteInputView mRemoteInputView;
        public boolean mShowImeOnInputConnection;
        public final ArraySet mSupportedMimes;
        public UserHandle mUser;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda1] */
        public RemoteEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mOnReceiveContentListener = new OnReceiveContentListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0
                @Override // android.view.OnReceiveContentListener
                public final ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
                    RemoteInputView.RemoteEditText remoteEditText = RemoteInputView.RemoteEditText.this;
                    int i = RemoteInputView.RemoteEditText.$r8$clinit;
                    remoteEditText.getClass();
                    Pair partition = contentInfo.partition(new RemoteInputView$RemoteEditText$$ExternalSyntheticLambda2());
                    ContentInfo contentInfo2 = (ContentInfo) partition.first;
                    ContentInfo contentInfo3 = (ContentInfo) partition.second;
                    if (contentInfo2 != null) {
                        remoteEditText.mRemoteInputView.setAttachment(contentInfo2);
                    }
                    return contentInfo3;
                }
            };
            this.mSupportedMimes = new ArraySet();
            this.mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda1
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    RemoteInputView.RemoteEditText remoteEditText = RemoteInputView.RemoteEditText.this;
                    int i = RemoteInputView.RemoteEditText.$r8$clinit;
                    remoteEditText.defocusIfNeeded(true);
                }
            };
            this.mLightBarController = (LightBarController) Dependency.get(LightBarController.class);
        }

        public final void defocusIfNeeded(boolean z) {
            RemoteInputView remoteInputView;
            RemoteInputView remoteInputView2 = this.mRemoteInputView;
            if ((remoteInputView2 != null && remoteInputView2.mEntry.row.mChangingPosition) || isTemporarilyDetached()) {
                if (!isTemporarilyDetached() || (remoteInputView = this.mRemoteInputView) == null) {
                    return;
                }
                remoteInputView.mEntry.remoteInputText = getText();
                return;
            }
            if (isFocusable() && isEnabled()) {
                setFocusableInTouchMode(false);
                setFocusable(false);
                setCursorVisible(false);
                RemoteInputView remoteInputView3 = this.mRemoteInputView;
                if (remoteInputView3 != null) {
                    remoteInputView3.onDefocus(z, true, null);
                }
                this.mShowImeOnInputConnection = false;
            }
        }

        @Override // android.widget.TextView, android.view.View
        public final void getFocusedRect(Rect rect) {
            super.getFocusedRect(rect);
            int i = ((EditText) this).mScrollY;
            rect.top = i;
            rect.bottom = (((EditText) this).mBottom - ((EditText) this).mTop) + i;
        }

        @Override // android.widget.TextView, android.view.View
        public final boolean onCheckIsTextEditor() {
            RemoteInputView remoteInputView = this.mRemoteInputView;
            if (remoteInputView != null) {
                Object obj = RemoteInputView.VIEW_TAG;
                remoteInputView.getClass();
            }
            return super.onCheckIsTextEditor();
        }

        @Override // android.widget.TextView
        public final void onCommitCompletion(CompletionInfo completionInfo) {
            clearComposingText();
            setText(completionInfo.getText());
            setSelection(getText().length());
        }

        @Override // android.widget.TextView, android.view.View
        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            Context context;
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            try {
                Context context2 = ((EditText) this).mContext;
                context = context2.createPackageContextAsUser(context2.getPackageName(), 0, this.mUser);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("RemoteInput", "Unable to create user context:" + e.getMessage(), e);
                context = null;
            }
            if (this.mShowImeOnInputConnection && onCreateInputConnection != null) {
                if (context == null) {
                    context = getContext();
                }
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(InputMethodManager.class);
                this.mInputMethodManager = inputMethodManager;
                if (inputMethodManager != null) {
                    post(new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.RemoteEditText.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteEditText remoteEditText = RemoteEditText.this;
                            remoteEditText.mInputMethodManager.viewClicked(remoteEditText);
                            RemoteEditText remoteEditText2 = RemoteEditText.this;
                            remoteEditText2.mInputMethodManager.showSoftInput(remoteEditText2, 0);
                        }
                    });
                }
            }
            return onCreateInputConnection;
        }

        @Override // android.widget.TextView, android.view.View
        public final void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            RemoteInputView remoteInputView = this.mRemoteInputView;
            if (remoteInputView != null) {
                Object obj = RemoteInputView.VIEW_TAG;
                remoteInputView.getClass();
                Iterator it = new ArrayList(remoteInputView.mEditTextFocusChangeListeners).iterator();
                while (it.hasNext()) {
                    ((View.OnFocusChangeListener) it.next()).onFocusChange(this, z);
                }
            }
            if (!z) {
                defocusIfNeeded(true);
            }
            RemoteInputView remoteInputView2 = this.mRemoteInputView;
            if (remoteInputView2 != null) {
                Object obj2 = RemoteInputView.VIEW_TAG;
                remoteInputView2.getClass();
                LightBarController lightBarController = this.mLightBarController;
                if (lightBarController.mDirectReplying == z) {
                    return;
                }
                lightBarController.mDirectReplying = z;
                lightBarController.reevaluate();
            }
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public final boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 4) {
                return true;
            }
            return super.onKeyDown(i, keyEvent);
        }

        @Override // android.widget.TextView, android.view.View
        public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1) {
                defocusIfNeeded(true);
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public final boolean onKeyUp(int i, KeyEvent keyEvent) {
            if (i != 4) {
                return super.onKeyUp(i, keyEvent);
            }
            defocusIfNeeded(true);
            return true;
        }

        @Override // android.widget.TextView, android.view.View
        public final void onVisibilityChanged(View view, int i) {
            super.onVisibilityChanged(view, i);
            if (isShown()) {
                return;
            }
            defocusIfNeeded(false);
        }

        @Override // android.view.View
        public final boolean requestRectangleOnScreen(Rect rect) {
            RemoteInputView remoteInputView = this.mRemoteInputView;
            RemoteInputController remoteInputController = remoteInputView.mController;
            NotificationEntry notificationEntry = remoteInputView.mEntry;
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (notificationStackScrollLayout.mForcedScroll == expandableNotificationRow) {
                return true;
            }
            notificationStackScrollLayout.mForcedScroll = expandableNotificationRow;
            if (notificationStackScrollLayout.mAnimatedInsets) {
                notificationStackScrollLayout.updateForcedScroll();
                return true;
            }
            notificationStackScrollLayout.scrollTo(expandableNotificationRow);
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RevealParams {
        public final int centerX;
        public final int centerY;
        public final int radius;

        public RevealParams(int i, int i2, int i3) {
            this.centerX = i;
            this.centerY = i2;
            this.radius = i3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SendButtonTextWatcher implements TextWatcher {
        public /* synthetic */ SendButtonTextWatcher(RemoteInputView remoteInputView, int i) {
            this();
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            RemoteInputView remoteInputView = RemoteInputView.this;
            Object obj = RemoteInputView.VIEW_TAG;
            remoteInputView.updateSendButton();
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String str = RemoteInputView.this.mEditText.getText().toString() + RemoteInputView.this.mSignature;
            int length = str.length();
            RemoteInputView remoteInputView = RemoteInputView.this;
            if (remoteInputView.mIsSms) {
                remoteInputView.getClass();
                if (SmsMessage.calculateLength(str, false)[0] > 1) {
                    RemoteInputView remoteInputView2 = RemoteInputView.this;
                    String str2 = remoteInputView2.mPrevString;
                    if (str2 != null) {
                        remoteInputView2.mEditText.setText(str2);
                        RemoteInputView remoteInputView3 = RemoteInputView.this;
                        remoteInputView3.mEditText.setSelection(remoteInputView3.mPrevString.length());
                    }
                    RemoteInputView.m1727$$Nest$mshowExceedTextLimitToast(RemoteInputView.this);
                    return;
                }
            } else {
                int i4 = remoteInputView.mMaxLength;
                if (i4 > 0 && length > i4) {
                    remoteInputView.mEditText.setText(remoteInputView.mPrevString);
                    RemoteInputView remoteInputView4 = RemoteInputView.this;
                    remoteInputView4.mEditText.setSelection(remoteInputView4.mPrevString.length());
                    RemoteInputView.m1727$$Nest$mshowExceedTextLimitToast(RemoteInputView.this);
                    return;
                }
            }
            RemoteInputView remoteInputView5 = RemoteInputView.this;
            remoteInputView5.mPrevString = remoteInputView5.mEditText.getText().toString();
        }

        private SendButtonTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: -$$Nest$mshowExceedTextLimitToast, reason: not valid java name */
    public static void m1727$$Nest$mshowExceedTextLimitToast(RemoteInputView remoteInputView) {
        String string = remoteInputView.getContext().getResources().getString(R.string.noti_direct_reply_exceed_text_limit_toast);
        if (remoteInputView.mTextLimitToast == null) {
            remoteInputView.mTextLimitToast = Toast.makeText(remoteInputView.getContext(), string, 1);
        }
        remoteInputView.mTextLimitToast.setText(string);
        remoteInputView.mTextLimitToast.show();
    }

    public RemoteInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mToken = new Object();
        this.mOnSendListeners = new ArrayList();
        this.mOnVisibilityChangedListeners = new ArrayList();
        this.mEditTextFocusChangeListeners = new ArrayList();
        int i = 0;
        this.mIsAnimatingAppearance = false;
        this.mTextLimitToast = null;
        this.mPrevString = "";
        this.mSignature = "";
        this.mTextWatcher = new SendButtonTextWatcher(this, i);
        this.mEditorActionHandler = new EditorActionHandler(this, i);
        this.mUiEventLogger = (UiEventLogger) Dependency.get(UiEventLogger.class);
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent, android.R.^attr-private.colorSurface});
        this.mTint = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchFinishTemporaryDetach() {
        if (isAttachedToWindow()) {
            RemoteEditText remoteEditText = this.mEditText;
            attachViewToParent(remoteEditText, 0, remoteEditText.getLayoutParams());
        } else {
            removeDetachedView(this.mEditText, false);
        }
        super.dispatchFinishTemporaryDetach();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        int indexOfChild = indexOfChild(this.mEditText);
        if (indexOfChild != -1) {
            detachViewFromParent(indexOfChild);
        }
    }

    public final void focus() {
        this.mUiEventLogger.logWithInstanceId(NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_OPEN, this.mEntry.mSbn.getUid(), this.mEntry.mSbn.getPackageName(), this.mEntry.mSbn.getInstanceId());
        setVisibility(0);
        NotificationViewWrapper notificationViewWrapper = this.mWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setRemoteInputVisible(true);
        }
        RemoteEditText remoteEditText = this.mEditText;
        remoteEditText.setFocusableInTouchMode(true);
        remoteEditText.setFocusable(true);
        remoteEditText.setCursorVisible(true);
        remoteEditText.requestFocus();
        RemoteEditText remoteEditText2 = this.mEditText;
        remoteEditText2.mShowImeOnInputConnection = true;
        remoteEditText2.setText(this.mEntry.remoteInputText);
        RemoteEditText remoteEditText3 = this.mEditText;
        remoteEditText3.setSelection(remoteEditText3.length());
        this.mEditText.requestFocus();
        RemoteInputController remoteInputController = this.mController;
        NotificationEntry notificationEntry = this.mEntry;
        Object obj = this.mToken;
        remoteInputController.getClass();
        Objects.requireNonNull(notificationEntry);
        Objects.requireNonNull(obj);
        boolean pruneWeakThenRemoveAndContains = remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        boolean pruneWeakThenRemoveAndContains2 = remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, obj);
        remoteInputController.mLogger.logAddRemoteInput(notificationEntry.mKey, pruneWeakThenRemoveAndContains, pruneWeakThenRemoveAndContains2);
        if (!pruneWeakThenRemoveAndContains2) {
            remoteInputController.mOpen.add(new Pair(new WeakReference(notificationEntry), obj));
        }
        if (!pruneWeakThenRemoveAndContains) {
            remoteInputController.apply(notificationEntry);
        }
        setAttachment(this.mEntry.remoteInputAttachment);
        updateSendButton();
    }

    public final CharSequence getText() {
        return this.mEditText.getText();
    }

    public final ViewRootImpl getViewRootImpl() {
        ViewRootImpl viewRootImpl = this.mTestableViewRootImpl;
        return viewRootImpl != null ? viewRootImpl : super.getViewRootImpl();
    }

    public final boolean isActive() {
        return this.mEditText.isFocused() && this.mEditText.isEnabled();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setEditTextReferenceToSelf();
        this.mEditText.setOnEditorActionListener(this.mEditorActionHandler);
        this.mEditText.addTextChangedListener(this.mTextWatcher);
        if (this.mEntry.row.mChangingPosition && getVisibility() == 0 && this.mEditText.isFocusable()) {
            this.mEditText.requestFocus();
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == this.mSendButton) {
            Iterator it = new ArrayList(this.mOnSendListeners).iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }
    }

    public void onDefocus(boolean z, boolean z2, final Runnable runnable) {
        RevealParams revealParams;
        int i;
        this.mController.removeRemoteInput(this.mEntry, this.mToken);
        this.mEntry.remoteInputText = this.mEditText.getText();
        ViewGroup viewGroup = (ViewGroup) getParent();
        int i2 = 0;
        if (z && viewGroup != null && this.mIsFocusAnimationFlagActive) {
            final ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            ViewGroup viewGroup3 = (ViewGroup) getParent();
            View findViewById = viewGroup3 == null ? null : viewGroup3.findViewById(android.R.id.activity_chooser_view_content);
            int height = (findViewById != null ? findViewById.getHeight() : 0) - getHeight();
            if (getLayoutParams() instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
                layoutParams.topMargin = height;
                setLayoutParams(layoutParams);
            }
            if (viewGroup2 != null) {
                viewGroup2.setClipChildren(false);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0f, 0.0f);
            ofFloat.setDuration(83L);
            ofFloat.setStartDelay(120L);
            LinearInterpolator linearInterpolator = InterpolatorsAndroidX.LINEAR;
            ofFloat.setInterpolator(linearInterpolator);
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.5f);
            ofFloat2.addUpdateListener(new RemoteInputView$$ExternalSyntheticLambda0(this, ofFloat2, i2));
            ofFloat2.setDuration(360L);
            ofFloat2.setInterpolator(InterpolatorsAndroidX.FAST_OUT_SLOW_IN);
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.6
                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Object obj = RemoteInputView.VIEW_TAG;
                    RemoteInputView.this.setFocusAnimationScaleY(1.0f);
                }
            });
            if (findViewById == null) {
                animatorSet.playTogether(ofFloat, ofFloat2);
            } else {
                findViewById.forceHasOverlappingRendering(false);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(findViewById, View.ALPHA, 0.0f, 1.0f);
                ofFloat3.setDuration(83L);
                ofFloat3.setInterpolator(linearInterpolator);
                ofFloat3.setStartDelay(180L);
                animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.2
                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd$1(Animator animator) {
                    Object obj = RemoteInputView.VIEW_TAG;
                    RemoteInputView remoteInputView = RemoteInputView.this;
                    if (remoteInputView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) remoteInputView.getLayoutParams();
                        layoutParams2.topMargin = 0;
                        remoteInputView.setLayoutParams(layoutParams2);
                    }
                    ViewGroup viewGroup4 = viewGroup2;
                    if (viewGroup4 != null) {
                        viewGroup4.setClipChildren(true);
                    }
                    remoteInputView.setVisibility(8);
                    NotificationViewWrapper notificationViewWrapper = remoteInputView.mWrapper;
                    if (notificationViewWrapper != null) {
                        notificationViewWrapper.setRemoteInputVisible(false);
                    }
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            if (findViewById != null) {
                findViewById.setAlpha(0.0f);
            }
            animatorSet.start();
        } else if (!z || (revealParams = this.mRevealParams) == null || (i = revealParams.radius) <= 0) {
            setVisibility(8);
            if (runnable != null) {
                runnable.run();
            }
            NotificationViewWrapper notificationViewWrapper = this.mWrapper;
            if (notificationViewWrapper != null) {
                notificationViewWrapper.setRemoteInputVisible(false);
            }
        } else {
            android.animation.Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(this, revealParams.centerX, revealParams.centerY, i, 0.0f);
            createCircularReveal.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            createCircularReveal.setDuration(150L);
            createCircularReveal.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(android.animation.Animator animator) {
                    RemoteInputView.this.setVisibility(8);
                    NotificationViewWrapper notificationViewWrapper2 = RemoteInputView.this.mWrapper;
                    if (notificationViewWrapper2 != null) {
                        notificationViewWrapper2.setRemoteInputVisible(false);
                    }
                }
            });
            createCircularReveal.start();
        }
        if (z2) {
            this.mUiEventLogger.logWithInstanceId(NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_CLOSE, this.mEntry.mSbn.getUid(), this.mEntry.mSbn.getPackageName(), this.mEntry.mSbn.getInstanceId());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mEditText.removeTextChangedListener(this.mTextWatcher);
        this.mEditText.setOnEditorActionListener(null);
        this.mEditText.mRemoteInputView = null;
        if (this.mEntry.row.mChangingPosition || isTemporarilyDetached()) {
            return;
        }
        this.mController.removeRemoteInput(this.mEntry, this.mToken);
        this.mController.removeSpinning(this.mToken, this.mEntry.mKey);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mProgressBar = (ProgressBar) findViewById(R.id.remote_input_progress);
        TextView textView = (TextView) findViewById(R.id.remote_input_send);
        this.mSendButton = textView;
        textView.setOnClickListener(this);
        this.mContentBackground = (GradientDrawable) ((LinearLayout) this).mContext.getDrawable(R.drawable.remote_input_view_text_bg).mutate();
        this.mDelete = (ImageView) findViewById(R.id.remote_input_delete);
        ((ImageView) findViewById(R.id.remote_input_delete_bg)).setImageTintBlendMode(BlendMode.SRC_IN);
        this.mDelete.setImageTintBlendMode(BlendMode.SRC_IN);
        this.mDelete.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RemoteInputView remoteInputView = RemoteInputView.this;
                Object obj = RemoteInputView.VIEW_TAG;
                remoteInputView.setAttachment(null);
            }
        });
        this.mContentView = (LinearLayout) findViewById(R.id.remote_input_content);
        RemoteEditText remoteEditText = (RemoteEditText) findViewById(R.id.remote_input_text);
        this.mEditText = remoteEditText;
        remoteEditText.setPrivateImeOptions("disableSticker=true;disableGifKeyboard=true");
        RemoteEditText remoteEditText2 = this.mEditText;
        remoteEditText2.setFocusableInTouchMode(false);
        remoteEditText2.setFocusable(false);
        remoteEditText2.setCursorVisible(false);
        this.mEditText.setEnabled(false);
        this.mEditText.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(0) { // from class: com.android.systemui.statusbar.policy.RemoteInputView.1
            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                super.onEnd(windowInsetsAnimation);
                if (windowInsetsAnimation.getTypeMask() == WindowInsets.Type.ime()) {
                    RemoteInputView remoteInputView = RemoteInputView.this;
                    boolean z = false;
                    remoteInputView.mEntry.mRemoteEditImeAnimatingAway = false;
                    WindowInsets rootWindowInsets = remoteInputView.mEditText.getRootWindowInsets();
                    if (rootWindowInsets == null) {
                        Log.w("RemoteInput", "onEnd called on detached view", new Exception());
                    }
                    NotificationEntry notificationEntry = RemoteInputView.this.mEntry;
                    if (rootWindowInsets != null && rootWindowInsets.isVisible(WindowInsets.Type.ime())) {
                        z = true;
                    }
                    notificationEntry.mRemoteEditImeVisible = z;
                    RemoteInputView remoteInputView2 = RemoteInputView.this;
                    NotificationEntry notificationEntry2 = remoteInputView2.mEntry;
                    if (notificationEntry2.mRemoteEditImeVisible || remoteInputView2.mEditText.mShowImeOnInputConnection) {
                        return;
                    }
                    remoteInputView2.mController.removeRemoteInput(notificationEntry2, remoteInputView2.mToken);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
                return windowInsets;
            }
        });
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mView.cancelLongPress();
            notificationStackScrollLayoutController.mView.mDisallowDismissInThisMotion = true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mIsFocusAnimationFlagActive) {
            setPivotY(getMeasuredHeight());
        }
        Rect rect = this.mContentBackgroundBounds;
        if (rect != null) {
            this.mContentBackground.setBounds(rect);
        }
    }

    public final void onNotificationUpdateOrReset() {
        NotificationViewWrapper notificationViewWrapper;
        if (this.mProgressBar.getVisibility() == 0) {
            if (this.mIsFocusAnimationFlagActive) {
                this.mProgressBar.setVisibility(4);
                this.mResetting = true;
                this.mSending = false;
                this.mController.removeSpinning(this.mToken, this.mEntry.mKey);
                onDefocus(true, false, new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputView remoteInputView = RemoteInputView.this;
                        remoteInputView.mEntry.remoteInputTextWhenReset = SpannedString.valueOf(remoteInputView.mEditText.getText());
                        remoteInputView.mEditText.getText().clear();
                        remoteInputView.mEditText.setEnabled(remoteInputView.isAggregatedVisible());
                        remoteInputView.mSendButton.setVisibility(0);
                        remoteInputView.updateSendButton();
                        remoteInputView.setAttachment(null);
                        remoteInputView.mResetting = false;
                    }
                });
            } else {
                this.mResetting = true;
                this.mSending = false;
                this.mEntry.remoteInputTextWhenReset = SpannedString.valueOf(this.mEditText.getText());
                this.mEditText.getText().clear();
                this.mEditText.setEnabled(isAggregatedVisible());
                this.mSendButton.setVisibility(0);
                this.mProgressBar.setVisibility(4);
                this.mController.removeSpinning(this.mToken, this.mEntry.mKey);
                updateSendButton();
                onDefocus(false, false, null);
                setAttachment(null);
                this.mResetting = false;
            }
        }
        if (!isActive() || (notificationViewWrapper = this.mWrapper) == null) {
            return;
        }
        notificationViewWrapper.setRemoteInputVisible(true);
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.mResetting && view == this.mEditText) {
            return false;
        }
        return super.onRequestSendAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        if (z) {
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(1000000, this.mEditText.mOnBackInvokedCallback);
            }
        } else {
            ViewRootImpl viewRootImpl2 = getViewRootImpl();
            if (viewRootImpl2 != null) {
                viewRootImpl2.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mEditText.mOnBackInvokedCallback);
            }
        }
        super.onVisibilityAggregated(z);
        this.mEditText.setEnabled(z && !this.mSending);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this) {
            Iterator it = new ArrayList(this.mOnVisibilityChangedListeners).iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Boolean.valueOf(i == 0));
            }
            if (i == 0 || this.mController.isRemoteInputActive$1()) {
                return;
            }
            RemoteEditText remoteEditText = this.mEditText;
            int i2 = RemoteEditText.$r8$clinit;
            WindowInsetsController windowInsetsController = remoteEditText.getWindowInsetsController();
            if (windowInsetsController != null) {
                windowInsetsController.hide(WindowInsets.Type.ime());
            }
        }
    }

    public void setAttachment(ContentInfo contentInfo) {
        ContentInfo contentInfo2 = this.mEntry.remoteInputAttachment;
        if (contentInfo2 != null && contentInfo2 != contentInfo) {
            contentInfo2.releasePermissions();
        }
        NotificationEntry notificationEntry = this.mEntry;
        notificationEntry.remoteInputAttachment = contentInfo;
        if (contentInfo != null) {
            notificationEntry.remoteInputUri = contentInfo.getClip().getItemAt(0).getUri();
            this.mEntry.remoteInputMimeType = contentInfo.getClip().getDescription().getMimeType(0);
        }
        View findViewById = findViewById(R.id.remote_input_content_container);
        ImageView imageView = (ImageView) findViewById(R.id.remote_input_attachment_image);
        imageView.setImageDrawable(null);
        if (contentInfo == null) {
            findViewById.setVisibility(8);
            return;
        }
        imageView.setImageURI(contentInfo.getClip().getItemAt(0).getUri());
        if (imageView.getDrawable() == null) {
            findViewById.setVisibility(8);
        } else {
            findViewById.setVisibility(0);
            this.mUiEventLogger.logWithInstanceId(NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_ATTACH_IMAGE, this.mEntry.mSbn.getUid(), this.mEntry.mSbn.getPackageName(), this.mEntry.mSbn.getInstanceId());
        }
        updateSendButton();
    }

    public final void setBackgroundTintColor(int i, int i2, boolean z) {
        if (z == this.mColorized && i == this.mTint) {
            return;
        }
        this.mColorized = z;
        this.mTint = i;
        if (i2 == 0) {
            i2 = ((LinearLayout) this).mContext.getColor(R.color.sec_default_remote_input_background);
        }
        setBackgroundColor(ContrastColorUtil.ensureTextBackgroundColor(i2, ((LinearLayout) this).mContext.getColor(R.color.sec_remote_input_text_enabled), ((LinearLayout) this).mContext.getColor(R.color.sec_remote_input_hint)));
    }

    public void setEditTextReferenceToSelf() {
        this.mEditText.mRemoteInputView = this;
    }

    public final void setFocusAnimationScaleY(float f) {
        int height = (int) ((1.0f - f) * 0.5f * this.mContentView.getHeight());
        Rect rect = new Rect(0, height, this.mContentView.getWidth(), this.mContentView.getHeight() - height);
        this.mContentBackground.setBounds(rect);
        if (f == 1.0f) {
            this.mContentBackgroundBounds = null;
        } else {
            this.mContentBackgroundBounds = rect;
        }
        setTranslationY(height);
    }

    public final void setSupportedMimeTypes(Collection collection) {
        String[] strArr;
        RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0 remoteInputView$RemoteEditText$$ExternalSyntheticLambda0;
        RemoteEditText remoteEditText = this.mEditText;
        if (collection != null) {
            remoteEditText.getClass();
            if (!collection.isEmpty()) {
                strArr = (String[]) collection.toArray(new String[0]);
                remoteInputView$RemoteEditText$$ExternalSyntheticLambda0 = remoteEditText.mOnReceiveContentListener;
                remoteEditText.setOnReceiveContentListener(strArr, remoteInputView$RemoteEditText$$ExternalSyntheticLambda0);
                remoteEditText.mSupportedMimes.clear();
                remoteEditText.mSupportedMimes.addAll(collection);
            }
        }
        strArr = null;
        remoteInputView$RemoteEditText$$ExternalSyntheticLambda0 = null;
        remoteEditText.setOnReceiveContentListener(strArr, remoteInputView$RemoteEditText$$ExternalSyntheticLambda0);
        remoteEditText.mSupportedMimes.clear();
        remoteEditText.mSupportedMimes.addAll(collection);
    }

    public void setViewRootImpl(ViewRootImpl viewRootImpl) {
        this.mTestableViewRootImpl = viewRootImpl;
    }

    public final void updateRemoteInputLimitToastResources(RemoteInput remoteInput) {
        this.mMaxLength = remoteInput.getExtras().getInt("maxLength", 200);
        this.mIsSms = remoteInput.getExtras().getBoolean("isSms", false);
        this.mSignature = remoteInput.getExtras().getString(Account.SIGNATURE, "");
    }

    public final void updateSendButton() {
        this.mSendButton.setEnabled((this.mEditText.length() == 0 && this.mEntry.remoteInputAttachment == null) ? false : true);
    }
}
