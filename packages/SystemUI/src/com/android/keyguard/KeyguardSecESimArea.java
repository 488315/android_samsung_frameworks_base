package com.android.keyguard;

import android.app.PendingIntent;
import android.app.SemWallpaperColors;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecESimArea;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetCallback;

class KeyguardSecESimArea extends FrameLayout implements SystemUIWidgetCallback {
    public KeyguardSecurityCallback mCallback;
    public SystemUITextView mESimText;
    public final EuiccManager mEuiccManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mPendingEsimTextVisible;
    public ProgressBar mProgressBar;
    public final AnonymousClass1 mReceiver;
    public KeyguardSecurityModel.SecurityMode mSecurityMode;
    public SelectedUserInteractor mSelectedUserInteractor;
    public int mSubscriptionId;

    /* renamed from: com.android.keyguard.KeyguardSecESimArea$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.android.keyguard.disable_esim".equals(intent.getAction())) {
                KeyguardSecESimArea.this.mProgressBar.setVisibility(8);
                int resultCode = getResultCode();
                if (resultCode != 0) {
                    ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(resultCode, "Error disabling esim, result code = ", "KeyguardSecEsimArea");
                    int intExtra = intent.getIntExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", 0);
                    ListPopupWindow$$ExternalSyntheticOutline0.m(intExtra, "switch detailedCode: ", "KeyguardSecEsimArea");
                    if (intExtra == 10001) {
                        SystemUIDialog systemUIDialog = new SystemUIDialog(((FrameLayout) KeyguardSecESimArea.this).mContext);
                        systemUIDialog.setMessage(KeyguardSecESimArea.this.getResources().getString(R.string.kg_esim_memory_reset_message_1) + "\n\n" + KeyguardSecESimArea.this.getResources().getString(R.string.kg_esim_memory_reset_message_2) + "\n\n" + KeyguardSecESimArea.this.getResources().getString(R.string.kg_esim_memory_reset_message_3));
                        systemUIDialog.setCancelable(false);
                        systemUIDialog.setNegativeButton(android.R.string.cancel, null);
                        systemUIDialog.setPositiveButton(R.string.kg_esim_memory_reset_remove_button, new DialogInterface.OnClickListener() { // from class: com.android.keyguard.KeyguardSecESimArea$1$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                KeyguardSecESimArea.AnonymousClass1 anonymousClass1 = KeyguardSecESimArea.AnonymousClass1.this;
                                anonymousClass1.getClass();
                                Log.d("KeyguardSecEsimArea", "onClick - Remove button");
                                KeyguardSecESimArea.this.mKeyguardUpdateMonitor.removeESim(KeyguardSecESimArea.this.mKeyguardUpdateMonitor.getNextSubIdForState(2));
                            }
                        });
                        systemUIDialog.getWindow().setType(2009);
                        systemUIDialog.show();
                    }
                    KeyguardSecESimArea.this.mESimText.setVisibility(0);
                } else {
                    Log.d("KeyguardSecEsimArea", "Success ACTION_DISABLE_ESIM");
                    KeyguardSecESimArea keyguardSecESimArea = KeyguardSecESimArea.this;
                    if (keyguardSecESimArea.mCallback != null) {
                        if (keyguardSecESimArea.mKeyguardUpdateMonitor.isSimState(2) || KeyguardSecESimArea.this.mKeyguardUpdateMonitor.isSimState(3) || KeyguardSecESimArea.this.mKeyguardUpdateMonitor.isSecure()) {
                            KeyguardSecESimArea.this.mCallback.reset();
                            KeyguardSecESimArea keyguardSecESimArea2 = KeyguardSecESimArea.this;
                            if (keyguardSecESimArea2.mPendingEsimTextVisible) {
                                keyguardSecESimArea2.mESimText.setVisibility(0);
                            }
                        } else {
                            Log.d("KeyguardSecEsimArea", "Dismiss SIM PIN/PUK View");
                            SelectedUserInteractor selectedUserInteractor = KeyguardSecESimArea.this.mSelectedUserInteractor;
                            int selectedUserId = selectedUserInteractor != null ? selectedUserInteractor.getSelectedUserId(false) : 0;
                            KeyguardSecESimArea keyguardSecESimArea3 = KeyguardSecESimArea.this;
                            keyguardSecESimArea3.mCallback.dismiss(true, selectedUserId, keyguardSecESimArea3.mSecurityMode);
                        }
                    }
                }
                KeyguardSecESimArea.this.mPendingEsimTextVisible = false;
            }
            if ("com.android.keyguard.euicc_reset".equals(intent.getAction())) {
                KeyguardSecESimArea.this.mProgressBar.setVisibility(8);
                int resultCode2 = getResultCode();
                if (resultCode2 != 0) {
                    ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(resultCode2, "Error euicc memory reset, result code = ", "KeyguardSecEsimArea");
                    KeyguardSecESimArea.this.mESimText.setVisibility(0);
                } else {
                    Log.d("KeyguardSecEsimArea", "Success ACTION_EUICC_RESET");
                }
                KeyguardSecESimArea.this.mPendingEsimTextVisible = false;
            }
        }
    }

    /* renamed from: $r8$lambda$xYLrsvIfM53IKX71aim7tERO9-A, reason: not valid java name */
    public static /* synthetic */ void m836$r8$lambda$xYLrsvIfM53IKX71aim7tERO9A(KeyguardSecESimArea keyguardSecESimArea) {
        keyguardSecESimArea.getClass();
        Log.d("KeyguardSecEsimArea", "onClick - skip button");
        KeyguardSecurityCallback keyguardSecurityCallback = keyguardSecESimArea.mCallback;
        if (keyguardSecurityCallback != null) {
            keyguardSecurityCallback.userActivity();
        }
        keyguardSecESimArea.mESimText.setVisibility(8);
        keyguardSecESimArea.mProgressBar.setVisibility(0);
        SubscriptionInfo activeSubscriptionInfo = SubscriptionManager.from(((FrameLayout) keyguardSecESimArea).mContext).getActiveSubscriptionInfo(keyguardSecESimArea.mSubscriptionId);
        if (activeSubscriptionInfo == null) {
            Log.e("KeyguardSecEsimArea", "No active subscription with subscriptionId: " + keyguardSecESimArea.mSubscriptionId);
        } else {
            Intent intent = new Intent("com.android.keyguard.disable_esim");
            intent.setPackage(((FrameLayout) keyguardSecESimArea).mContext.getPackageName());
            keyguardSecESimArea.mEuiccManager.switchToSubscription(-1, activeSubscriptionInfo.getPortIndex(), PendingIntent.getBroadcastAsUser(((FrameLayout) keyguardSecESimArea).mContext, 0, intent, 201326592, UserHandle.SYSTEM));
        }
    }

    public KeyguardSecESimArea(Context context) {
        this(context, null);
    }

    public final void eraseSubscriptions() {
        this.mEuiccManager.eraseSubscriptions(PendingIntent.getBroadcastAsUser(((FrameLayout) this).mContext, 0, new Intent("com.android.keyguard.euicc_reset"), 201326592, UserHandle.SYSTEM));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(this.mReceiver, new IntentFilter("com.android.keyguard.disable_esim"), null, null, 2, "com.android.systemui.permission.SELF");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mReceiver);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mESimText = (SystemUITextView) findViewById(R.id.esim_text);
        SpannableString spannableString = new SpannableString(this.mESimText.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        this.mESimText.setMaxFontScale(1.1f);
        this.mESimText.setText(spannableString);
        this.mESimText.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSecESimArea$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardSecESimArea.m836$r8$lambda$xYLrsvIfM53IKX71aim7tERO9A(KeyguardSecESimArea.this);
            }
        });
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.skip_progress);
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            if (this.mProgressBar.getVisibility() == 0) {
                this.mPendingEsimTextVisible = true;
            } else {
                this.mESimText.setVisibility(0);
                this.mPendingEsimTextVisible = false;
            }
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    public KeyguardSecESimArea(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardSecESimArea(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public KeyguardSecESimArea(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingEsimTextVisible = false;
        this.mReceiver = new AnonymousClass1();
        this.mEuiccManager = (EuiccManager) context.getSystemService("euicc");
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
    }
}
