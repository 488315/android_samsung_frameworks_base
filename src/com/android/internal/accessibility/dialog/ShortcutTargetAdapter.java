package com.android.internal.accessibility.dialog;

import java.util.List;

/* loaded from: classes5.dex */
class ShortcutTargetAdapter extends TargetAdapter {
    private int mShortcutMenuMode = 0;
    private final List<AccessibilityTarget> mTargets;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    ShortcutTargetAdapter(List<AccessibilityTarget> list) {
        this.mTargets = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mTargets.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mTargets.get(i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ec, code lost:
    
        if (android.provider.Settings.System.getIntForUser(r1.getContentResolver(), r13, 0, -2) == 1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0104, code lost:
    
        r17.mShortcutMenuMode = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00f7, code lost:
    
        if (android.provider.Settings.Secure.getIntForUser(r1.getContentResolver(), r13, 0, -2) == 1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0102, code lost:
    
        if (android.provider.Settings.Global.getInt(r1.getContentResolver(), r13, 0) == 1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c6, code lost:
    
        if (r11.equals("system") == false) goto L24;
     */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View getView(int r18, android.view.View r19, android.view.ViewGroup r20) {
        /*
            Method dump skipped, instructions count: 456
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.accessibility.dialog.ShortcutTargetAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    void setShortcutMenuMode(int i) {
        this.mShortcutMenuMode = i;
    }

    int getShortcutMenuMode() {
        return this.mShortcutMenuMode;
    }
}
