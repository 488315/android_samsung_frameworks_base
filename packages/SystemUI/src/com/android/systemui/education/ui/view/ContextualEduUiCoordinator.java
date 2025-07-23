package com.android.systemui.education.ui.view;

import android.R;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.ui.viewmodel.ContextualEduToastViewModel;
import com.android.systemui.education.ui.viewmodel.ContextualEduViewModel;
import com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ContextualEduUiCoordinator implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final Function1 createDialog;
    public Dialog dialog;
    public final NotificationManager notificationManager;
    public final ContextualEduViewModel viewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GestureType.values().length];
            try {
                iArr[GestureType.BACK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GestureType.HOME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[GestureType.ALL_APPS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[GestureType.OVERVIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public ContextualEduUiCoordinator(CoroutineScope coroutineScope, ContextualEduViewModel contextualEduViewModel, Context context, NotificationManager notificationManager, Function1 function1) {
        this.applicationScope = coroutineScope;
        this.viewModel = contextualEduViewModel;
        this.context = context;
        this.notificationManager = notificationManager;
        this.createDialog = function1;
    }

    public final Intent createKeyboardTouchpadTutorialIntent(String str) {
        Intent intent = new Intent(this.context, (Class<?>) KeyboardTouchpadTutorialActivity.class);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(268435456);
        intent.putExtra("tutorial_scope", str);
        intent.putExtra("entry_point", "contextual_edu");
        return intent;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.notificationManager.createNotificationChannel(new NotificationChannel("ContextualEduNotificationChannel", this.context.getString(R.string.chooseUsbActivity), 2));
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new ContextualEduUiCoordinator$start$1(this, null), 7);
    }

    public ContextualEduUiCoordinator(CoroutineScope coroutineScope, final Context context, ContextualEduViewModel contextualEduViewModel, NotificationManager notificationManager, final AccessibilityManager accessibilityManager) {
        this(coroutineScope, contextualEduViewModel, context, notificationManager, new Function1() { // from class: com.android.systemui.education.ui.view.ContextualEduUiCoordinator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context2 = context;
                AccessibilityManager accessibilityManager2 = accessibilityManager;
                int i = ContextualEduUiCoordinator.$r8$clinit;
                return new ContextualEduDialog(context2, (ContextualEduToastViewModel) obj, accessibilityManager2);
            }
        });
    }
}
