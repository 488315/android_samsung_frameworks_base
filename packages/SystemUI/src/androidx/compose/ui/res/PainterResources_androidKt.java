package androidx.compose.ui.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.BitmapPainter;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.ImageVectorCache;
import java.lang.ref.WeakReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class PainterResources_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:66:0x011f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Painter painterResource(int i, Composer composer, int i2) {
        TypedValue typedValue;
        boolean z;
        Painter painterRememberVectorPainter;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.painterResource (PainterResources.android.kt:56)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Resources resources = (Resources) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResources);
        ResourceIdCache resourceIdCache = (ResourceIdCache) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResourceIdCache);
        synchronized (resourceIdCache) {
            typedValue = (TypedValue) resourceIdCache.resIdPathMap.get(i);
            z = true;
            if (typedValue == null) {
                typedValue = new TypedValue();
                resources.getValue(i, typedValue, true);
                MutableIntObjectMap mutableIntObjectMap = resourceIdCache.resIdPathMap;
                int iFindAbsoluteInsertIndex = mutableIntObjectMap.findAbsoluteInsertIndex(i);
                Object[] objArr = mutableIntObjectMap.values;
                Object obj = objArr[iFindAbsoluteInsertIndex];
                mutableIntObjectMap.keys[iFindAbsoluteInsertIndex] = i;
                objArr[iFindAbsoluteInsertIndex] = typedValue;
            }
        }
        CharSequence charSequence = typedValue.string;
        if (charSequence == null || !StringsKt__StringsKt.endsWith$default(charSequence, ".xml")) {
            composerImpl.startReplaceGroup(-803006939);
            Object theme = context.getTheme();
            boolean zChanged = composerImpl.changed(charSequence);
            if ((((i2 & 14) ^ 6) <= 4 || !composerImpl.changed(i)) && (i2 & 6) != 4) {
                z = false;
            }
            boolean zChanged2 = composerImpl.changed(theme) | zChanged | z;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged2) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    try {
                        int i3 = ImageBitmap.$r8$clinit;
                        objRememberedValue = new AndroidImageBitmap(((BitmapDrawable) resources.getDrawable(i, null)).getBitmap());
                        composerImpl.updateRememberedValue(objRememberedValue);
                    } catch (Exception e) {
                        throw new ResourceResolutionException("Error attempting to load resource: " + ((Object) charSequence), e);
                    }
                }
                BitmapPainter bitmapPainter = new BitmapPainter((ImageBitmap) objRememberedValue, 0L, 0L, 6, null);
                composerImpl.end(false);
                painterRememberVectorPainter = bitmapPainter;
            }
        } else {
            composerImpl.startReplaceGroup(-803162373);
            Resources.Theme theme2 = context.getTheme();
            int i4 = typedValue.changingConfigurations;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.res.loadVectorResource (PainterResources.android.kt:87)");
            }
            ImageVectorCache imageVectorCache = (ImageVectorCache) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalImageVectorCache);
            ImageVectorCache.Key key = new ImageVectorCache.Key(theme2, i);
            WeakReference weakReference = (WeakReference) imageVectorCache.map.get(key);
            ImageVectorCache.ImageVectorEntry imageVectorEntryLoadVectorResourceInner = weakReference != null ? (ImageVectorCache.ImageVectorEntry) weakReference.get() : null;
            if (imageVectorEntryLoadVectorResourceInner == null) {
                XmlResourceParser xml = resources.getXml(i);
                int next = xml.next();
                while (next != 2 && next != 1) {
                    next = xml.next();
                }
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!Intrinsics.areEqual(xml.getName(), "vector")) {
                    throw new IllegalArgumentException("Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG, WEBP");
                }
                imageVectorEntryLoadVectorResourceInner = VectorResources_androidKt.loadVectorResourceInner(theme2, resources, xml, i4);
                imageVectorCache.map.put(key, new WeakReference(imageVectorEntryLoadVectorResourceInner));
            }
            boolean zIsTraceInProgress = ComposerKt.isTraceInProgress();
            ImageVector imageVector = imageVectorEntryLoadVectorResourceInner.imageVector;
            if (zIsTraceInProgress) {
                ComposerKt.traceEventEnd();
            }
            painterRememberVectorPainter = VectorPainterKt.rememberVectorPainter(imageVector, composerImpl);
            composerImpl.end(false);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return painterRememberVectorPainter;
    }
}
