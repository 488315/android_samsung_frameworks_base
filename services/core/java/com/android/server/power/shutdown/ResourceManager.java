package com.android.server.power.shutdown;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ResourceManager {
    public static final String chameleonCode;
    public static final String knoxAnimPath;
    public static final String knoxSoundPath;
    public static final String knoxSubAnimPath;
    public static final boolean supportChameleon;
    public final List mainImages;
    public final File mainLoopImage;
    public final AnimationPlayer player;
    public final List subImages;
    public final File subLoopImage;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005a, code lost:

       if (r0.equals("310120") == false) goto L15;
    */
    static {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.shutdown.ResourceManager.<clinit>():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x003a, code lost:

       if (addToPlaylistIfExists(r8.toString(), r0) != false) goto L90;
    */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0211 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ResourceManager(com.android.server.power.shutdown.AnimationPlayer r13) {
        /*
            Method dump skipped, instructions count: 560
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.shutdown.ResourceManager.<init>(com.android.server.power.shutdown.AnimationPlayer):void");
    }

    public static boolean addToPlaylistIfExists(String str, List list) {
        File file = new File(str);
        if (file.exists()) {
            return list.add(file);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0038 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String search(java.lang.String r7) {
        /*
            java.lang.String r0 = "/carrier/chameleon.xml"
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 != 0) goto L80
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 == 0) goto L11
            goto L80
        L11:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            javax.xml.parsers.DocumentBuilderFactory r0 = javax.xml.parsers.DocumentBuilderFactory.newInstance()
            javax.xml.parsers.DocumentBuilder r0 = r0.newDocumentBuilder()     // Catch: java.lang.Exception -> L2d
            boolean r3 = r1.exists()     // Catch: java.lang.Exception -> L2d
            if (r3 == 0) goto L35
            org.w3c.dom.Document r0 = r0.parse(r1)     // Catch: java.lang.Exception -> L2d
            org.w3c.dom.Element r0 = r0.getDocumentElement()     // Catch: java.lang.Exception -> L2d
            goto L36
        L2d:
            r0 = move-exception
            java.lang.String r1 = "Shutdown-ResourceManager"
            java.lang.String r3 = "Exception"
            android.util.Slog.e(r1, r3, r0)
        L35:
            r0 = r2
        L36:
            if (r0 != 0) goto L39
            return r2
        L39:
            java.util.StringTokenizer r1 = new java.util.StringTokenizer
            java.lang.String r3 = "."
            r1.<init>(r7, r3)
            boolean r7 = r1.hasMoreTokens()
            if (r7 == 0) goto L74
        L46:
            boolean r7 = r1.hasMoreTokens()
            if (r7 == 0) goto L74
            java.lang.String r7 = r1.nextToken()
            org.w3c.dom.NodeList r0 = r0.getChildNodes()
            if (r0 == 0) goto L70
            int r3 = r0.getLength()
            r4 = 0
        L5b:
            if (r4 >= r3) goto L70
            org.w3c.dom.Node r5 = r0.item(r4)
            java.lang.String r6 = r5.getNodeName()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L6d
            r0 = r5
            goto L71
        L6d:
            int r4 = r4 + 1
            goto L5b
        L70:
            r0 = r2
        L71:
            if (r0 != 0) goto L46
            return r2
        L74:
            org.w3c.dom.Node r7 = r0.getFirstChild()
            if (r7 != 0) goto L7b
            return r2
        L7b:
            java.lang.String r7 = r7.getNodeValue()
            return r7
        L80:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.shutdown.ResourceManager.search(java.lang.String):java.lang.String");
    }

    public final boolean addMainAniIfExists(String str, String[] strArr) {
        boolean z = false;
        for (String str2 : strArr) {
            z |=
                    addToPlaylistIfExists(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, str2),
                            this.mainImages);
        }
        return z;
    }

    public final void addSubAniIfExists(String str, String[] strArr) {
        for (String str2 : strArr) {
            addToPlaylistIfExists(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, str2),
                    this.subImages);
        }
    }
}
