package android.view;

/* loaded from: classes4.dex */
class ViewTraversalTracingStrings {
    public final String classSimpleName;
    public final String onLayout;
    public final String onMeasure;
    public final String onMeasureBeforeLayout;
    public final String requestLayoutStacktracePrefix;

    ViewTraversalTracingStrings(View view) {
        String simpleName = view.getClass().getSimpleName();
        this.classSimpleName = simpleName;
        this.onMeasureBeforeLayout = getTraceName("onMeasureBeforeLayout", simpleName, view);
        this.onMeasure = getTraceName("onMeasure", simpleName, view);
        this.onLayout = getTraceName("onLayout", simpleName, view);
        this.requestLayoutStacktracePrefix = "requestLayout " + simpleName;
    }

    private String getTraceName(String str, String str2, View view) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        view.appendId(sb);
        return sb.substring(0, Math.min(sb.length(), 127));
    }
}
