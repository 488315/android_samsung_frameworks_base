package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Property;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.widget.SystemUIEditText;
import com.samsung.android.knox.accounts.Account;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenNotificationReplyActivity extends Activity implements CommandQueue.Callbacks {
    public static final String TAG;
    public final SubscreenNotificationController controller;
    public SystemUIEditText editText;
    public NotificationEntry entry;
    public InputMethodManager imm;
    public boolean isForce;
    public boolean isSent;
    public boolean isSms;
    public final SubscreenNotificationReplyActivity$mWindowInsetsAnimationCallback$1 mWindowInsetsAnimationCallback;
    public int maxLength;
    public final NotifPipeline notifPipeLine;
    public LinearLayout replyLayout;
    public ImageView sendButton;
    public SubscreenSubRoomNotification subRoomNoti;
    public Toast toast;
    public String key = "";
    public CharSequence prevText = "";
    public String signature = "";
    public final SubscreenNotificationReplyActivity$displayListener$1 displayListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$displayListener$1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("isFolderOpened: ", SubscreenNotificationReplyActivity.TAG, z);
            if (z) {
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                subscreenNotificationReplyActivity.isForce = true;
                subscreenNotificationReplyActivity.finish();
            }
        }
    };
    public final SubscreenNotificationReplyActivity$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent != null) {
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                if (Intrinsics.areEqual(intent.getAction(), PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS) && Intrinsics.areEqual(intent.getStringExtra("reason"), "homekey")) {
                    InputMethodManager inputMethodManager = subscreenNotificationReplyActivity.imm;
                    if (inputMethodManager != null) {
                        inputMethodManager.semForceHideSoftInput();
                    }
                    subscreenNotificationReplyActivity.isForce = true;
                    subscreenNotificationReplyActivity.finish();
                }
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "SubscreenNotificationReplyActivity";
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$displayListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$mWindowInsetsAnimationCallback$1] */
    public SubscreenNotificationReplyActivity(SubscreenNotificationController subscreenNotificationController, NotifPipeline notifPipeline) {
        this.controller = subscreenNotificationController;
        this.notifPipeLine = notifPipeline;
        Log.d(TAG, "SubscreenNotificationReplyActivity()");
        this.mWindowInsetsAnimationCallback = new WindowInsetsAnimation.Callback() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$mWindowInsetsAnimationCallback$1
            public int imeBottom;

            {
                super(0);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                SystemUIEditText systemUIEditText;
                if (this.imeBottom != 0 || (systemUIEditText = SubscreenNotificationReplyActivity.this.editText) == null) {
                    return;
                }
                systemUIEditText.setMaxLines(4);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                SystemUIEditText systemUIEditText = SubscreenNotificationReplyActivity.this.editText;
                if (systemUIEditText != null) {
                    systemUIEditText.setMaxLines(2);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
                this.imeBottom = windowInsets.getInsets(8).bottom;
                return windowInsets;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                return bounds;
            }
        };
    }

    public static final void access$performBackClicked(final SubscreenNotificationReplyActivity subscreenNotificationReplyActivity) {
        InputMethodManager inputMethodManager = subscreenNotificationReplyActivity.imm;
        if (inputMethodManager != null) {
            inputMethodManager.semForceHideSoftInput();
        }
        LinearLayout linearLayout = subscreenNotificationReplyActivity.replyLayout;
        if (linearLayout == null) {
            linearLayout = null;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) View.ALPHA, 1.0f, 0.0f);
        ofFloat.setDuration(300L);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$performBackClicked$lambda$11$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SubscreenNotificationReplyActivity.this.finish();
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
        ofFloat.start();
    }

    public static final void access$showExceedTextLimitToast(SubscreenNotificationReplyActivity subscreenNotificationReplyActivity) {
        String string = subscreenNotificationReplyActivity.getResources().getString(R.string.noti_direct_reply_exceed_text_limit_toast);
        Toast toast = subscreenNotificationReplyActivity.toast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(subscreenNotificationReplyActivity, string, 1);
        subscreenNotificationReplyActivity.toast = makeText;
        if (makeText != null) {
            makeText.show();
        }
        Log.d(TAG, "showExceedTextLimitToast. current text = " + ((Object) subscreenNotificationReplyActivity.prevText));
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        ImageView imageView;
        ImageView imageView2;
        Integer valueOf = keyEvent != null ? Integer.valueOf(keyEvent.getAction()) : null;
        if (valueOf != null && valueOf.intValue() == 1 && keyEvent.getKeyCode() == 66 && (imageView = this.sendButton) != null && imageView.isFocused() && (imageView2 = this.sendButton) != null) {
            imageView2.performClick();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void enableSendButton() {
        ImageView imageView = this.sendButton;
        if (imageView != null) {
            SystemUIEditText systemUIEditText = this.editText;
            boolean z = String.valueOf(systemUIEditText != null ? systemUIEditText.getText() : null).length() > 0;
            imageView.setEnabled(z);
            imageView.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String str = TAG;
        Log.d(str, "onCreate()");
        this.imm = (InputMethodManager) getSystemService("input_method");
        setContentView(R.layout.subscreen_notification_reply_activity);
        setShowWhenLocked(true);
        SubscreenNotificationController subscreenNotificationController = this.controller;
        SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
        ImageView imageView = null;
        SubscreenSubRoomNotification subRoomNotification = subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null;
        this.subRoomNoti = subRoomNotification;
        if (subRoomNotification == null) {
            subRoomNotification = null;
        }
        subRoomNotification.mSubscreenMainLayout.setVisibility(8);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.reply_activity_main_layout);
        this.replyLayout = linearLayout;
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            if (linearLayout == null) {
                linearLayout = null;
            }
            subscreenDeviceModelParent2.updateMainHeaderView(linearLayout);
            Context baseContext = getBaseContext();
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.subRoomNoti;
            if (subscreenSubRoomNotification == null) {
                subscreenSubRoomNotification = null;
            }
            subscreenDeviceModelParent2.initMainHeaderViewItems(baseContext, subscreenSubRoomNotification.mNotificationDetailAdapter.mSelectNotificationInfo, true);
            subscreenDeviceModelParent2.updateMainHeaderViewVisibility(0);
            subscreenDeviceModelParent2.setStartedReplyActivity();
            LinearLayout linearLayout2 = this.replyLayout;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            subscreenDeviceModelParent2.setDimOnMainBackground(linearLayout2);
        }
        LinearLayout linearLayout3 = this.replyLayout;
        if (linearLayout3 == null) {
            linearLayout3 = null;
        }
        ObjectAnimator.ofFloat(linearLayout3, (Property<LinearLayout, Float>) View.ALPHA, 0.5f, 1.0f).setDuration(250L).start();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.key = extras.getString("key", "");
            this.maxLength = extras.getInt("maxLength");
            this.isSms = extras.getBoolean("isSms");
            this.signature = extras.getString(Account.SIGNATURE);
        }
        NotificationEntry entry = this.notifPipeLine.mNotifCollection.getEntry(this.key);
        this.entry = entry;
        if (entry != null) {
            this.prevText = entry.remoteInputText;
        }
        String str2 = this.key;
        CharSequence charSequence = this.prevText;
        Log.d(str, "Reply Info. key = " + str2 + ", prevText = " + ((Object) charSequence) + ", maxLength = " + this.maxLength + ", isSms = " + this.isSms + ", signature = " + this.signature);
        int intForUser = Settings.System.getIntForUser(getContentResolver(), "screen_off_timeout", 10000, -2);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (attributes != null) {
            attributes.semSetScreenTimeout(intForUser);
            attributes.semSetScreenDimDuration(0L);
            attributes.privateFlags |= 16;
            attributes.layoutInDisplayCutoutMode = 3;
            getWindow().setAttributes(attributes);
        }
        getWindow().setSoftInputMode(4);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.back_key);
        if (linearLayout4 != null) {
            linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubscreenNotificationReplyActivity.access$performBackClicked(SubscreenNotificationReplyActivity.this);
                }
            });
        }
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.header_app_icon_layout);
        if (frameLayout != null) {
            frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubscreenNotificationReplyActivity.access$performBackClicked(SubscreenNotificationReplyActivity.this);
                }
            });
        }
        final SystemUIEditText systemUIEditText = (SystemUIEditText) findViewById(R.id.edit_responses);
        if (systemUIEditText != null) {
            systemUIEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$3$1
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z) {
                    InputMethodManager inputMethodManager;
                    if (z && view.isPressed() && (inputMethodManager = SubscreenNotificationReplyActivity.this.imm) != null) {
                        inputMethodManager.showSoftInput(view, 1);
                    }
                }
            });
            systemUIEditText.addTextChangedListener(new TextWatcher() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$3$2
                @Override // android.text.TextWatcher
                public final void onTextChanged(CharSequence charSequence2, int i, int i2, int i3) {
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                    String str3 = SubscreenNotificationReplyActivity.TAG;
                    subscreenNotificationReplyActivity.enableSendButton();
                    Editable text = systemUIEditText.getText();
                    String str4 = ((Object) text) + SubscreenNotificationReplyActivity.this.signature;
                    int length = str4.length();
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity2 = SubscreenNotificationReplyActivity.this;
                    if (subscreenNotificationReplyActivity2.isSms) {
                        if (SmsMessage.calculateLength(str4, false)[0] > 1) {
                            SubscreenNotificationReplyActivity.this.setPrevText();
                            SubscreenNotificationReplyActivity.access$showExceedTextLimitToast(SubscreenNotificationReplyActivity.this);
                            if (SubscreenNotificationReplyActivity.this.prevText == null) {
                                systemUIEditText.setText((CharSequence) null);
                                return;
                            }
                            return;
                        }
                    } else if (length > subscreenNotificationReplyActivity2.maxLength) {
                        subscreenNotificationReplyActivity2.setPrevText();
                        SubscreenNotificationReplyActivity.access$showExceedTextLimitToast(SubscreenNotificationReplyActivity.this);
                        if (SubscreenNotificationReplyActivity.this.prevText == null) {
                            systemUIEditText.setText((CharSequence) null);
                            return;
                        }
                        return;
                    }
                    SubscreenNotificationReplyActivity.this.prevText = systemUIEditText.getText().toString();
                }

                @Override // android.text.TextWatcher
                public final void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public final void beforeTextChanged(CharSequence charSequence2, int i, int i2, int i3) {
                }
            });
            systemUIEditText.setWindowInsetsAnimationCallback(this.mWindowInsetsAnimationCallback);
        } else {
            systemUIEditText = null;
        }
        this.editText = systemUIEditText;
        final ImageView imageView2 = (ImageView) findViewById(R.id.reply_send_button);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$4$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Editable text;
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                    if (subscreenNotificationReplyActivity.isSent) {
                        return;
                    }
                    InputMethodManager inputMethodManager = subscreenNotificationReplyActivity.imm;
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(imageView2.getWindowToken(), 0);
                    }
                    SystemUIEditText systemUIEditText2 = SubscreenNotificationReplyActivity.this.editText;
                    Integer num = null;
                    String valueOf = String.valueOf(systemUIEditText2 != null ? systemUIEditText2.getText() : null);
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity2 = SubscreenNotificationReplyActivity.this;
                    final boolean useHistory = subscreenNotificationReplyActivity2.controller.useHistory(subscreenNotificationReplyActivity2.entry);
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity3 = SubscreenNotificationReplyActivity.this;
                    SubscreenNotificationController subscreenNotificationController2 = subscreenNotificationReplyActivity3.controller;
                    String str3 = subscreenNotificationReplyActivity3.key;
                    str3.getClass();
                    subscreenNotificationController2.replyNotification(str3, valueOf);
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity4 = SubscreenNotificationReplyActivity.this;
                    subscreenNotificationReplyActivity4.isSent = true;
                    LinearLayout linearLayout5 = subscreenNotificationReplyActivity4.replyLayout;
                    if (linearLayout5 == null) {
                        linearLayout5 = null;
                    }
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout5, (Property<LinearLayout, Float>) View.ALPHA, 1.0f, 0.0f);
                    final SubscreenNotificationReplyActivity subscreenNotificationReplyActivity5 = SubscreenNotificationReplyActivity.this;
                    ofFloat.setDuration(300L);
                    ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$4$1$onClick$lambda$1$$inlined$doOnEnd$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            SubscreenDeviceModelParent subscreenDeviceModelParent3;
                            if (!useHistory && (subscreenDeviceModelParent3 = subscreenNotificationReplyActivity5.controller.mDeviceModel) != null) {
                                subscreenDeviceModelParent3.hideDetailNotificationAnimated(0, true);
                            }
                            subscreenNotificationReplyActivity5.finish();
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
                    ofFloat.start();
                    SystemUIEditText systemUIEditText3 = SubscreenNotificationReplyActivity.this.editText;
                    if (systemUIEditText3 != null && (text = systemUIEditText3.getText()) != null) {
                        num = Integer.valueOf(text.length());
                    }
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY_WITH_KEYBORAD, "length", String.valueOf(num));
                }
            });
            imageView = imageView2;
        }
        this.sendButton = imageView;
        setPrevText();
        enableSendButton();
        SystemUIEditText systemUIEditText2 = this.editText;
        if (systemUIEditText2 != null) {
            systemUIEditText2.requestFocus();
        }
        subscreenNotificationController.replyActivity = this;
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(this.displayListener);
        ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).addCallback((CommandQueue.Callbacks) this);
        registerReceiver(this.broadcastReceiver, new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), 2);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        String valueOf;
        Log.d(TAG, "onDestroy()");
        NotificationEntry notificationEntry = this.entry;
        if (notificationEntry != null) {
            if (this.isSent) {
                valueOf = "";
            } else {
                SystemUIEditText systemUIEditText = this.editText;
                valueOf = String.valueOf(systemUIEditText != null ? systemUIEditText.getText() : null);
            }
            notificationEntry.remoteInputText = valueOf;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.subRoomNoti;
        if (subscreenSubRoomNotification == null) {
            subscreenSubRoomNotification = null;
        }
        subscreenSubRoomNotification.mSubscreenMainLayout.setVisibility(0);
        SubscreenNotificationController subscreenNotificationController = this.controller;
        subscreenNotificationController.replyActivity = null;
        SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            subscreenDeviceModelParent.replyActivityFinished(this.isForce);
        }
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).removeObserver(this.displayListener);
        ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).removeCallback((CommandQueue.Callbacks) this);
        unregisterReceiver(this.broadcastReceiver);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public final void onStop() {
        Toast toast = this.toast;
        if (toast != null) {
            toast.cancel();
        }
        super.onStop();
    }

    public final void setPrevText() {
        CharSequence charSequence = this.prevText;
        if (charSequence != null) {
            SystemUIEditText systemUIEditText = this.editText;
            if (systemUIEditText != null) {
                systemUIEditText.setText(charSequence);
            }
            SystemUIEditText systemUIEditText2 = this.editText;
            if (systemUIEditText2 != null) {
                systemUIEditText2.setSelection(charSequence.length());
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, int i2, int i3, boolean z) {
    }
}
