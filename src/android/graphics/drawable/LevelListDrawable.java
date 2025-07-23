package android.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class LevelListDrawable extends DrawableContainer {
    private LevelListState mLevelListState;
    private boolean mMutated;

    public LevelListDrawable() {
        this(null, null);
    }

    public void addLevel(int i, int i2, Drawable drawable) {
        if (drawable != null) {
            this.mLevelListState.addLevel(i, i2, drawable);
            onLevelChange(getLevel());
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        if (selectDrawable(this.mLevelListState.indexOfLevel(i))) {
            return true;
        }
        return super.onLevelChange(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateDensity(resources);
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0098, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0091, code lost:
    
        onLevelChange(getLevel());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void inflateChildElements(android.content.res.Resources r8, org.xmlpull.v1.XmlPullParser r9, android.util.AttributeSet r10, android.content.res.Resources.Theme r11) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r7 = this;
            int r0 = r9.getDepth()
            r1 = 1
            int r0 = r0 + r1
        L6:
            int r2 = r9.next()
            if (r2 == r1) goto L91
            int r3 = r9.getDepth()
            if (r3 >= r0) goto L15
            r4 = 3
            if (r2 == r4) goto L91
        L15:
            r4 = 2
            if (r2 == r4) goto L19
            goto L6
        L19:
            if (r3 > r0) goto L6
            java.lang.String r2 = r9.getName()
            java.lang.String r3 = "item"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L28
            goto L6
        L28:
            int[] r2 = com.android.internal.R.styleable.LevelListDrawableItem
            android.content.res.TypedArray r2 = obtainAttributes(r8, r11, r10, r2)
            r3 = 0
            int r5 = r2.getInt(r1, r3)
            int r6 = r2.getInt(r4, r3)
            int r3 = r2.getResourceId(r3, r3)
            r2.recycle()
            if (r6 < 0) goto L76
            if (r3 == 0) goto L47
            android.graphics.drawable.Drawable r2 = r8.getDrawable(r3, r11)
            goto L55
        L47:
            int r2 = r9.next()
            r3 = 4
            if (r2 != r3) goto L4f
            goto L47
        L4f:
            if (r2 != r4) goto L5b
            android.graphics.drawable.Drawable r2 = android.graphics.drawable.Drawable.createFromXmlInner(r8, r9, r10, r11)
        L55:
            android.graphics.drawable.LevelListDrawable$LevelListState r3 = r7.mLevelListState
            r3.addLevel(r5, r6, r2)
            goto L6
        L5b:
            org.xmlpull.v1.XmlPullParserException r7 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = r9.getPositionDescription()
            r8.append(r9)
            java.lang.String r9 = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L76:
            org.xmlpull.v1.XmlPullParserException r7 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = r9.getPositionDescription()
            r8.append(r9)
            java.lang.String r9 = ": <item> tag requires a 'maxLevel' attribute"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L91:
            int r8 = r7.getLevel()
            r7.onLevelChange(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.LevelListDrawable.inflateChildElements(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLevelListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.DrawableContainer
    public LevelListState cloneConstantState() {
        return new LevelListState(this.mLevelListState, this, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    private static final class LevelListState extends DrawableContainer.DrawableContainerState {
        private int[] mHighs;
        private int[] mLows;

        LevelListState(LevelListState levelListState, LevelListDrawable levelListDrawable, Resources resources) {
            super(levelListState, levelListDrawable, resources);
            if (levelListState != null) {
                this.mLows = levelListState.mLows;
                this.mHighs = levelListState.mHighs;
            } else {
                this.mLows = new int[getCapacity()];
                this.mHighs = new int[getCapacity()];
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mutate() {
            this.mLows = (int[]) this.mLows.clone();
            this.mHighs = (int[]) this.mHighs.clone();
        }

        public void addLevel(int i, int i2, Drawable drawable) {
            int addChild = addChild(drawable);
            this.mLows[addChild] = i;
            this.mHighs[addChild] = i2;
        }

        public int indexOfLevel(int i) {
            int[] iArr = this.mLows;
            int[] iArr2 = this.mHighs;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (i >= iArr[i2] && i <= iArr2[i2]) {
                    return i2;
                }
            }
            return -1;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new LevelListDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new LevelListDrawable(this, resources);
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState
        public void growArray(int i, int i2) {
            super.growArray(i, i2);
            int[] iArr = new int[i2];
            System.arraycopy(this.mLows, 0, iArr, 0, i);
            this.mLows = iArr;
            int[] iArr2 = new int[i2];
            System.arraycopy(this.mHighs, 0, iArr2, 0, i);
            this.mHighs = iArr2;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof LevelListState) {
            this.mLevelListState = (LevelListState) drawableContainerState;
        }
    }

    private LevelListDrawable(LevelListState levelListState, Resources resources) {
        setConstantState(new LevelListState(levelListState, this, resources));
        onLevelChange(getLevel());
    }
}
