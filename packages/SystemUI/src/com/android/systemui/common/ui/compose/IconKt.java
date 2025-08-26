package com.android.systemui.common.ui.compose;

import android.graphics.drawable.Drawable;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.core.graphics.drawable.DrawableKt;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class IconKt {
    /* JADX WARN: Removed duplicated region for block: B:53:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0124  */
    /* renamed from: Icon-FNF3uiM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1074IconFNF3uiM(final Icon icon, Modifier modifier, long j, Composer composer, final int i, final int i2) {
        int i3;
        long j2;
        Modifier modifier2;
        long j3;
        final Modifier modifier3;
        final long j4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(862197981);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(icon) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            if ((i2 & 4) == 0) {
                j2 = j;
                int i5 = composerImpl.changed(j2) ? 256 : 128;
                i3 |= i5;
            } else {
                j2 = j;
            }
            i3 |= i5;
        } else {
            j2 = j;
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            j4 = j2;
            modifier3 = modifier;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    modifier = Modifier.Companion;
                }
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                    modifier2 = modifier;
                    j3 = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.Icon (Icon.kt:35)");
                }
                ContentDescription contentDescription = icon.getContentDescription();
                composerImpl.startReplaceGroup(-178121167);
                String strLoad = contentDescription != null ? null : ContentDescriptionKt.load(contentDescription, composerImpl);
                composerImpl.end(false);
                if (!(icon instanceof Icon.Loaded)) {
                    composerImpl.startReplaceGroup(-1226731870);
                    Icon.Loaded loaded = (Icon.Loaded) icon;
                    Drawable drawable = loaded.drawable;
                    composerImpl.startReplaceGroup(-178118321);
                    boolean zChanged = composerImpl.changed(drawable);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!zChanged) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            Drawable drawable2 = loaded.drawable;
                            objRememberedValue = new AndroidImageBitmap(DrawableKt.toBitmap(drawable2, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), null));
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        composerImpl.end(false);
                        androidx.compose.material3.IconKt.m269Iconww6aTOc((ImageBitmap) objRememberedValue, strLoad, modifier2, j3, composerImpl, (i3 << 3) & 8064, 0);
                        composerImpl.end(false);
                    }
                } else {
                    if (!(icon instanceof Icon.Resource)) {
                        composerImpl.startReplaceGroup(-178120466);
                        composerImpl.end(false);
                        throw new NoWhenBranchMatchedException();
                    }
                    composerImpl.startReplaceGroup(-178111730);
                    androidx.compose.material3.IconKt.m270Iconww6aTOc(PainterResources_androidKt.painterResource(((Icon.Resource) icon).res, composerImpl, 0), strLoad, modifier2, j3, composerImpl, (i3 << 3) & 8064, 0);
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier3 = modifier2;
                j4 = j3;
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                }
            }
            modifier2 = modifier;
            j3 = j2;
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
            }
            ContentDescription contentDescription2 = icon.getContentDescription();
            composerImpl.startReplaceGroup(-178121167);
            String strLoad2 = contentDescription2 != null ? null : ContentDescriptionKt.load(contentDescription2, composerImpl);
            composerImpl.end(false);
            if (!(icon instanceof Icon.Loaded)) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            modifier3 = modifier2;
            j4 = j3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.common.ui.compose.IconKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    long j5 = j4;
                    IconKt.m1074IconFNF3uiM(icon, modifier3, j5, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
