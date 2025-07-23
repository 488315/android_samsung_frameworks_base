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
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.platform.ComposeView;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogDelegate;
import com.android.systemui.util.Assert;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SystemUIDialogFactoryExtKt {
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x008b, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DragHandle(final android.app.Dialog r16, androidx.compose.runtime.Composer r17, final int r18) {
        /*
            r0 = r16
            r1 = r18
            r2 = 0
            r13 = r17
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r3 = 590125960(0x232c9b88, float:9.357074E-18)
            r13.startRestartGroup(r3)
            boolean r3 = r13.changedInstance(r0)
            r4 = 2
            if (r3 == 0) goto L18
            r3 = 4
            goto L19
        L18:
            r3 = r4
        L19:
            r3 = r3 | r1
            r3 = r3 & 3
            if (r3 != r4) goto L2a
            boolean r3 = r13.getSkipping()
            if (r3 != 0) goto L25
            goto L2a
        L25:
            r13.skipToGroupEnd()
            goto Lcd
        L2a:
            boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r3 == 0) goto L35
            java.lang.String r3 = "com.android.systemui.statusbar.phone.DragHandle (SystemUIDialogFactoryExt.kt:282)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r3)
        L35:
            r3 = 2131956747(0x7f13140b, float:1.9550058E38)
            java.lang.String r3 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r3, r13)
            androidx.compose.ui.Modifier$Companion r4 = androidx.compose.ui.Modifier.Companion
            r5 = 16
            float r6 = (float) r5
            androidx.compose.ui.unit.Dp$Companion r5 = androidx.compose.ui.unit.Dp.Companion
            r5 = 6
            float r8 = (float) r5
            r5 = 0
            r7 = 0
            r9 = 5
            androidx.compose.ui.Modifier r4 = androidx.compose.foundation.layout.PaddingKt.m128paddingqDBjuR0$default(r4, r5, r6, r7, r8, r9)
            r5 = 224537645(0xd622c2d, float:6.96948E-31)
            r13.startReplaceGroup(r5)
            boolean r5 = r13.changed(r3)
            java.lang.Object r6 = r13.rememberedValue()
            androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
            if (r5 != 0) goto L65
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r5 = androidx.compose.runtime.Composer.Companion.Empty
            if (r6 != r5) goto L6d
        L65:
            com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda0 r6 = new com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda0
            r6.<init>(r3, r2)
            r13.updateRememberedValue(r6)
        L6d:
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            r13.end(r2)
            androidx.compose.ui.Modifier r3 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r4, r2, r6)
            r4 = 224542684(0xd623fdc, float:6.9718495E-31)
            r13.startReplaceGroup(r4)
            boolean r4 = r13.changedInstance(r0)
            java.lang.Object r5 = r13.rememberedValue()
            if (r4 != 0) goto L8d
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r4) goto L95
        L8d:
            com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda1 r5 = new com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda1
            r5.<init>(r0)
            r13.updateRememberedValue(r5)
        L95:
            kotlin.jvm.functions.Function0 r5 = (kotlin.jvm.functions.Function0) r5
            r13.end(r2)
            r4 = 7
            r6 = 0
            androidx.compose.ui.Modifier r3 = androidx.compose.foundation.ClickableKt.m35clickableXHw0xAI$default(r3, r2, r6, r5, r4)
            androidx.compose.material3.MaterialTheme r2 = androidx.compose.material3.MaterialTheme.INSTANCE
            r2.getClass()
            androidx.compose.material3.ColorScheme r2 = androidx.compose.material3.MaterialTheme.getColorScheme(r13)
            long r5 = r2.onSurfaceVariant
            androidx.compose.material3.Shapes r2 = androidx.compose.material3.MaterialTheme.getShapes(r13)
            androidx.compose.foundation.shape.CornerBasedShape r4 = r2.extraLarge
            com.android.systemui.statusbar.phone.ComposableSingletons$SystemUIDialogFactoryExtKt r2 = com.android.systemui.statusbar.phone.ComposableSingletons$SystemUIDialogFactoryExtKt.INSTANCE
            r2.getClass()
            androidx.compose.runtime.internal.ComposableLambdaImpl r12 = com.android.systemui.statusbar.phone.ComposableSingletons$SystemUIDialogFactoryExtKt.f108lambda1
            r10 = 0
            r11 = 0
            r7 = 0
            r9 = 0
            r14 = 12582912(0xc00000, float:1.7632415E-38)
            r15 = 120(0x78, float:1.68E-43)
            androidx.compose.material3.SurfaceKt.m303SurfaceT9BRK9s(r3, r4, r5, r7, r9, r10, r11, r12, r13, r14, r15)
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto Lcd
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lcd:
            androidx.compose.runtime.RecomposeScopeImpl r2 = r13.endRestartGroup()
            if (r2 == 0) goto Lda
            com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda2 r3 = new com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda2
            r3.<init>(r0, r1)
            r2.block = r3
        Lda:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.DragHandle(android.app.Dialog, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003a, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.Modifier access$bottomSheetClickable(androidx.compose.ui.Modifier r3, final kotlin.jvm.functions.Function0 r4, androidx.compose.runtime.ComposerImpl r5, int r6) {
        /*
            r0 = -853331142(0xffffffffcd23333a, float:-1.7112771E8)
            r5.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L11
            java.lang.String r0 = "com.android.systemui.statusbar.phone.bottomSheetClickable (SystemUIDialogFactoryExt.kt:279)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L11:
            r0 = 554929629(0x21138ddd, float:4.999329E-19)
            r5.startReplaceGroup(r0)
            r0 = r6 & 112(0x70, float:1.57E-43)
            r0 = r0 ^ 48
            r1 = 32
            r2 = 0
            if (r0 <= r1) goto L26
            boolean r0 = r5.changed(r4)
            if (r0 != 0) goto L2a
        L26:
            r6 = r6 & 48
            if (r6 != r1) goto L2c
        L2a:
            r6 = 1
            goto L2d
        L2c:
            r6 = r2
        L2d:
            java.lang.Object r0 = r5.rememberedValue()
            if (r6 != 0) goto L3c
            androidx.compose.runtime.Composer$Companion r6 = androidx.compose.runtime.Composer.Companion
            r6.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r6 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r6) goto L44
        L3c:
            com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1 r0 = new com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1
            r0.<init>()
            r5.updateRememberedValue(r0)
        L44:
            androidx.compose.ui.input.pointer.PointerInputEventHandler r0 = (androidx.compose.ui.input.pointer.PointerInputEventHandler) r0
            r5.end(r2)
            androidx.compose.ui.Modifier r3 = androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt.pointerInput(r3, r4, r0)
            boolean r4 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r4 == 0) goto L56
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L56:
            r5.end(r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.access$bottomSheetClickable(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, androidx.compose.runtime.ComposerImpl, int):androidx.compose.ui.Modifier");
    }

    public static final ComponentSystemUIDialog create(SystemUIDialogFactory systemUIDialogFactory, Context context, int i, DialogDelegate dialogDelegate, final ComposableLambdaImpl composableLambdaImpl) {
        systemUIDialogFactory.getClass();
        Assert.isMainThread();
        final ComponentSystemUIDialog componentSystemUIDialog = new ComponentSystemUIDialog(context, i, true, systemUIDialogFactory.dialogManager, systemUIDialogFactory.sysUiState, systemUIDialogFactory.broadcastDispatcher, systemUIDialogFactory.dialogTransitionAnimator, dialogDelegate);
        componentSystemUIDialog.create();
        ComposeView composeView = new ComposeView(context, null, 0, 6, null);
        composeView.setContent(new ComposableLambdaImpl(-716517866, true, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.create.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:242)");
                }
                final Function3 function3 = Function3.this;
                final ComponentSystemUIDialog componentSystemUIDialog2 = componentSystemUIDialog;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1991354508, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.create.<anonymous>.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:243)");
                        }
                        MaterialTheme.INSTANCE.getClass();
                        ProvidedValue defaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(MaterialTheme.getColorScheme(composer2).onSurfaceVariant));
                        final Function3 function32 = Function3.this;
                        final ComponentSystemUIDialog componentSystemUIDialog3 = componentSystemUIDialog2;
                        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(955047244, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.create.2.1.1.1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj5, Object obj6) {
                                Composer composer3 = (Composer) obj5;
                                if ((((Number) obj6).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.create.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:245)");
                                }
                                Function3.this.invoke(componentSystemUIDialog3, composer3, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer2), composer2, 56);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composer), composer, 48, 1);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }));
        componentSystemUIDialog.setContentView(composeView);
        return componentSystemUIDialog;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$1] */
    public static ComponentSystemUIDialog create$default(SystemUIDialogFactory systemUIDialogFactory, Context context, final Integer num, ShortcutCustomizationDialogDelegate shortcutCustomizationDialogDelegate, ComposableLambdaImpl composableLambdaImpl, int i) {
        int i2;
        if ((i & 1) != 0) {
            context = systemUIDialogFactory.applicationContext;
        }
        if ((i & 2) != 0) {
            int i3 = SystemUIDialog.$r8$clinit;
            i2 = R.style.Theme_SystemUI_Dialog;
        } else {
            i2 = R.style.Theme_VolumePanel_Popup;
        }
        if ((i & 8) != 0) {
            num = null;
        }
        ShortcutCustomizationDialogDelegate shortcutCustomizationDialogDelegate2 = shortcutCustomizationDialogDelegate;
        if ((i & 16) != 0) {
            shortcutCustomizationDialogDelegate2 = new DialogDelegate() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$1
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
            };
        }
        return create(systemUIDialogFactory, context, i2, shortcutCustomizationDialogDelegate2, composableLambdaImpl);
    }

    /* renamed from: createBottomSheet-6ZxE2Lo$default, reason: not valid java name */
    public static ComponentSystemUIDialog m3078createBottomSheet6ZxE2Lo$default(SystemUIDialogFactory systemUIDialogFactory, final ComposableLambdaImpl composableLambdaImpl, final boolean z, final float f, int i) {
        Context context = systemUIDialogFactory.applicationContext;
        if ((i & 16) != 0) {
            z = true;
        }
        return create(systemUIDialogFactory, context, R.style.Theme_SystemUI_BottomSheet, new EdgeToEdgeDialogDelegate(), new ComposableLambdaImpl(2126763161, true, new Function3() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$createBottomSheet$1
            /* JADX WARN: Code restructure failed: missing block: B:13:0x005d, code lost:
            
                if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
             */
            @Override // kotlin.jvm.functions.Function3
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r18, java.lang.Object r19, java.lang.Object r20) {
                /*
                    Method dump skipped, instructions count: 446
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$createBottomSheet$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }));
    }
}
