package androidx.compose.material3;

import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.Icons$Filled;
import androidx.compose.material3.internal.Strings;
import androidx.compose.material3.internal.Strings_androidKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposableSingletons$SnackbarKt {
    public static final ComposableSingletons$SnackbarKt INSTANCE = new ComposableSingletons$SnackbarKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f16lambda1 = new ComposableLambdaImpl(984817901, false, new Function2() { // from class: androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda-1$1
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
                ComposerKt.traceEventStart("androidx.compose.material3.ComposableSingletons$SnackbarKt.lambda-1.<anonymous> (Snackbar.kt:228)");
            }
            Icons$Filled.INSTANCE.getClass();
            ImageVector imageVector = Icons$Filled._close;
            if (imageVector == null) {
                Dp.Companion companion = Dp.Companion;
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Close", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder m = ExpandMoreKt$$ExternalSyntheticOutline0.m(19.0f, 6.41f, 17.59f, 5.0f);
                m.lineTo(12.0f, 10.59f);
                m.lineTo(6.41f, 5.0f);
                m.lineTo(5.0f, 6.41f);
                m.lineTo(10.59f, 12.0f);
                m.lineTo(5.0f, 17.59f);
                m.lineTo(6.41f, 19.0f);
                m.lineTo(12.0f, 13.41f);
                m.lineTo(17.59f, 19.0f);
                ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(m, 19.0f, 17.59f, 13.41f, 12.0f);
                builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                Icons$Filled._close = imageVector;
            }
            int i2 = Strings.$r8$clinit;
            IconKt.m270Iconww6aTOc(imageVector, Strings_androidKt.m321getString2EP1pXo(R.string.m3c_snackbar_dismiss, composer), (Modifier) null, 0L, composer, 0, 12);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
