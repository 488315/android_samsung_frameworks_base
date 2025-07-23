package android.text.style;

/* loaded from: classes4.dex */
public interface TabStopSpan extends ParagraphStyle {
    int getTabStop();

    public static class Standard implements TabStopSpan {
        private int mTabOffset;

        public Standard(int i) {
            this.mTabOffset = i;
        }

        @Override // android.text.style.TabStopSpan
        public int getTabStop() {
            return this.mTabOffset;
        }

        public String toString() {
            return "TabStopSpan.Standard{tabOffset=" + getTabStop() + '}';
        }
    }
}
