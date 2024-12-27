package com.android.systemui.statusbar.phone;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.platform.ComposeView;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.util.Assert;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

public abstract class SystemUIDialogFactoryExtKt {
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0033, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.Modifier access$bottomSheetClickable(androidx.compose.ui.Modifier r3, kotlin.jvm.functions.Function0 r4, androidx.compose.runtime.Composer r5, int r6) {
        /*
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            r0 = -853331142(0xffffffffcd23333a, float:-1.71127712E8)
            r5.startReplaceGroup(r0)
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            r0 = -259198655(0xfffffffff08cf141, float:-3.4895648E29)
            r5.startReplaceGroup(r0)
            r0 = r6 & 112(0x70, float:1.57E-43)
            r0 = r0 ^ 48
            r1 = 32
            r2 = 0
            if (r0 <= r1) goto L1f
            boolean r0 = r5.changed(r4)
            if (r0 != 0) goto L23
        L1f:
            r6 = r6 & 48
            if (r6 != r1) goto L25
        L23:
            r6 = 1
            goto L26
        L25:
            r6 = r2
        L26:
            java.lang.Object r0 = r5.rememberedValue()
            if (r6 != 0) goto L35
            androidx.compose.runtime.Composer$Companion r6 = androidx.compose.runtime.Composer.Companion
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r6 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r6) goto L3e
        L35:
            com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1 r0 = new com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1
            r6 = 0
            r0.<init>(r4, r6)
            r5.updateRememberedValue(r0)
        L3e:
            kotlin.jvm.functions.Function2 r0 = (kotlin.jvm.functions.Function2) r0
            r5.end(r2)
            androidx.compose.ui.Modifier r3 = androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt.pointerInput(r3, r4, r0)
            r5.end(r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.access$bottomSheetClickable(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):androidx.compose.ui.Modifier");
    }

    public static final ComponentSystemUIDialog create(SystemUIDialogFactory systemUIDialogFactory, Context context, int i, boolean z, DialogDelegate dialogDelegate, final ComposableLambdaImpl composableLambdaImpl) {
        systemUIDialogFactory.getClass();
        Assert.isMainThread();
        final ComponentSystemUIDialog componentSystemUIDialog = new ComponentSystemUIDialog(context, i, z, systemUIDialogFactory.dialogManager, systemUIDialogFactory.sysUiState, systemUIDialogFactory.broadcastDispatcher, systemUIDialogFactory.dialogTransitionAnimator, dialogDelegate);
        componentSystemUIDialog.create();
        ComposeView composeView = new ComposeView(context, null, 0, 6, null);
        composeView.setContent(new ComposableLambdaImpl(-1860640285, true, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1$1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey = ComposerKt.invocation;
                final Function3 function3 = Function3.this;
                final ComponentSystemUIDialog componentSystemUIDialog2 = componentSystemUIDialog;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-962447399, composer, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1$1$1, kotlin.jvm.internal.Lambda] */
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 11) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        MaterialTheme.INSTANCE.getClass();
                        ProvidedValue defaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m383boximpl(MaterialTheme.getColorScheme(composer2).onSurfaceVariant));
                        final Function3 function32 = Function3.this;
                        final ComponentSystemUIDialog componentSystemUIDialog3 = componentSystemUIDialog2;
                        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(582245529, composer2, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.create.2.1.1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj5, Object obj6) {
                                Composer composer3 = (Composer) obj5;
                                if ((((Number) obj6).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                Function3.this.invoke(componentSystemUIDialog3, composer3, 8);
                                return Unit.INSTANCE;
                            }
                        }), composer2, 56);
                        return Unit.INSTANCE;
                    }
                }), composer, 48, 1);
                return Unit.INSTANCE;
            }
        }));
        componentSystemUIDialog.setContentView(composeView);
        return componentSystemUIDialog;
    }

    public static ComponentSystemUIDialog create$default(SystemUIDialogFactory systemUIDialogFactory, Context context, int i, final Integer num, ComposableLambdaImpl composableLambdaImpl, int i2) {
        if ((i2 & 1) != 0) {
            context = systemUIDialogFactory.applicationContext;
        }
        Context context2 = context;
        if ((i2 & 2) != 0) {
            int i3 = SystemUIDialog.$r8$clinit;
            i = R.style.Theme_SystemUI_Dialog;
        }
        int i4 = i;
        if ((i2 & 8) != 0) {
            num = null;
        }
        return create(systemUIDialogFactory, context2, i4, true, new DialogDelegate() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$1
            @Override // com.android.systemui.statusbar.phone.DialogDelegate
            public final void onCreate(Dialog dialog, Bundle bundle) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
                Integer num2 = num;
                if (num2 != null) {
                    int intValue = num2.intValue();
                    Window window = systemUIDialog.getWindow();
                    if (window != null) {
                        window.setGravity(intValue);
                    }
                }
            }
        }, composableLambdaImpl);
    }
}
