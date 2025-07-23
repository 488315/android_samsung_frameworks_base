package androidx.compose.ui.res;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class VectorResources_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:102:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x038c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.res.ImageVectorCache.ImageVectorEntry loadVectorResourceInner(android.content.res.Resources.Theme r37, android.content.res.Resources r38, android.content.res.XmlResourceParser r39, int r40) {
        /*
            Method dump skipped, instructions count: 1118
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.res.VectorResources_androidKt.loadVectorResourceInner(android.content.res.Resources$Theme, android.content.res.Resources, android.content.res.XmlResourceParser, int):androidx.compose.ui.res.ImageVectorCache$ImageVectorEntry");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0045, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.graphics.vector.ImageVector vectorResource(int r6, androidx.compose.runtime.Composer r7) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.ui.res.vectorResource (VectorResources.android.kt:48)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalContext
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            java.lang.Object r0 = r7.consume(r0)
            android.content.Context r0 = (android.content.Context) r0
            androidx.compose.runtime.ComputedProvidableCompositionLocal r1 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalResources
            java.lang.Object r1 = r7.consume(r1)
            android.content.res.Resources r1 = (android.content.res.Resources) r1
            android.content.res.Resources$Theme r0 = r0.getTheme()
            android.content.res.Configuration r2 = r1.getConfiguration()
            boolean r3 = r7.changed(r6)
            boolean r4 = r7.changed(r1)
            r3 = r3 | r4
            boolean r4 = r7.changed(r0)
            r3 = r3 | r4
            boolean r2 = r7.changed(r2)
            r2 = r2 | r3
            java.lang.Object r3 = r7.rememberedValue()
            if (r2 != 0) goto L47
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L71
        L47:
            android.util.TypedValue r2 = new android.util.TypedValue
            r2.<init>()
            r3 = 1
            r1.getValue(r6, r2, r3)
            android.content.res.XmlResourceParser r6 = r1.getXml(r6)
            int r4 = r6.next()
        L58:
            r5 = 2
            if (r4 == r5) goto L62
            if (r4 == r3) goto L62
            int r4 = r6.next()
            goto L58
        L62:
            if (r4 != r5) goto L7d
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            int r2 = r2.changingConfigurations
            androidx.compose.ui.res.ImageVectorCache$ImageVectorEntry r6 = loadVectorResourceInner(r0, r1, r6, r2)
            androidx.compose.ui.graphics.vector.ImageVector r3 = r6.imageVector
            r7.updateRememberedValue(r3)
        L71:
            androidx.compose.ui.graphics.vector.ImageVector r3 = (androidx.compose.ui.graphics.vector.ImageVector) r3
            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r6 == 0) goto L7c
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L7c:
            return r3
        L7d:
            org.xmlpull.v1.XmlPullParserException r6 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r7 = "No start tag found"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.res.VectorResources_androidKt.vectorResource(int, androidx.compose.runtime.Composer):androidx.compose.ui.graphics.vector.ImageVector");
    }
}
