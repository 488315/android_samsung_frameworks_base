package androidx.compose.animation.graphics.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.compose.animation.graphics.vector.AnimatedImageVector;
import androidx.compose.animation.graphics.vector.AnimatedVectorTarget;
import androidx.compose.animation.graphics.vector.compat.AndroidVectorResources;
import androidx.compose.animation.graphics.vector.compat.XmlPullParserUtils_androidKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.VectorResources_androidKt;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class AnimatedVectorResources_androidKt {
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final AnimatedImageVector animatedVectorResource(int i, Composer composer, int i2) {
        TypedArray typedArrayObtainAttributes;
        TypedArray typedArrayObtainAttributes2;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.res.animatedVectorResource (AnimatedVectorResources.android.kt:40)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Resources resources = (Resources) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResources);
        Resources.Theme theme = context.getTheme();
        boolean z = (((i2 & 112) ^ 48) > 32 && composerImpl.changed(i)) || (i2 & 48) == 32;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!z) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                XmlResourceParser xml = resources.getXml(i);
                XmlPullParserUtils_androidKt.seekToStartTag(xml);
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                AndroidVectorResources.INSTANCE.getClass();
                int[] iArr = AndroidVectorResources.STYLEABLE_ANIMATED_VECTOR_DRAWABLE;
                if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr, 0, 0)) == null) {
                    typedArrayObtainAttributes = resources.obtainAttributes(attributeSetAsAttributeSet, iArr);
                }
                try {
                    int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
                    ArrayList arrayList = new ArrayList();
                    xml.next();
                    while (!XmlPullParserUtils_androidKt.isAtEnd(xml) && (xml.getEventType() != 3 || !Intrinsics.areEqual(xml.getName(), "animated-vector"))) {
                        if (xml.getEventType() == 2 && Intrinsics.areEqual(xml.getName(), "target")) {
                            AndroidVectorResources.INSTANCE.getClass();
                            int[] iArr2 = AndroidVectorResources.STYLEABLE_ANIMATED_VECTOR_DRAWABLE_TARGET;
                            if (theme == null || (typedArrayObtainAttributes2 = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr2, 0, 0)) == null) {
                                typedArrayObtainAttributes2 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr2);
                            }
                            try {
                                String string = typedArrayObtainAttributes2.getString(0);
                                if (string == null) {
                                    string = "";
                                }
                                AnimatedVectorTarget animatedVectorTarget = new AnimatedVectorTarget(string, AnimatorResources_androidKt.loadAnimatorResource(typedArrayObtainAttributes2.getResourceId(1, 0), theme, resources));
                                typedArrayObtainAttributes2.recycle();
                                arrayList.add(animatedVectorTarget);
                            } catch (Throwable th) {
                                typedArrayObtainAttributes2.recycle();
                                throw th;
                            }
                        }
                        xml.next();
                    }
                    ImageVector.Companion companion = ImageVector.Companion;
                    TypedValue typedValue = new TypedValue();
                    resources.getValue(resourceId, typedValue, true);
                    XmlResourceParser xml2 = resources.getXml(resourceId);
                    int next = xml2.next();
                    while (next != 2 && next != 1) {
                        next = xml2.next();
                    }
                    if (next != 2) {
                        throw new XmlPullParserException("No start tag found");
                    }
                    Unit unit = Unit.INSTANCE;
                    AnimatedImageVector animatedImageVector = new AnimatedImageVector(VectorResources_androidKt.loadVectorResourceInner(theme, resources, xml2, typedValue.changingConfigurations).imageVector, arrayList);
                    typedArrayObtainAttributes.recycle();
                    composerImpl.updateRememberedValue(animatedImageVector);
                    objRememberedValue = animatedImageVector;
                } catch (Throwable th2) {
                    typedArrayObtainAttributes.recycle();
                    throw th2;
                }
            }
        }
        AnimatedImageVector animatedImageVector2 = (AnimatedImageVector) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return animatedImageVector2;
    }
}
