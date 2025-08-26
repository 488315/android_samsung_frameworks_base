package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import com.android.ims.ImsConfig;
import com.android.internal.R;
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
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateDensity(resources);
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0091, code lost:
    
        onLevelChange(getLevel());
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0098, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int depth;
        int next;
        Drawable drawableCreateFromXmlInner;
        int depth2 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1 || ((depth = xmlPullParser.getDepth()) < depth2 && next2 == 3)) {
                break;
            }
            if (next2 == 2 && depth <= depth2 && xmlPullParser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.LevelListDrawableItem);
                int i = typedArrayObtainAttributes.getInt(1, 0);
                int i2 = typedArrayObtainAttributes.getInt(2, 0);
                int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
                typedArrayObtainAttributes.recycle();
                if (i2 < 0) {
                    throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'maxLevel' attribute");
                }
                if (resourceId != 0) {
                    drawableCreateFromXmlInner = resources.getDrawable(resourceId, theme);
                } else {
                    do {
                        next = xmlPullParser.next();
                    } while (next == 4);
                    if (next != 2) {
                        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    }
                    drawableCreateFromXmlInner = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                }
                this.mLevelListState.addLevel(i, i2, drawableCreateFromXmlInner);
            }
        }
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
            int iAddChild = addChild(drawable);
            this.mLows[iAddChild] = i;
            this.mHighs[iAddChild] = i2;
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

        /* JADX WARN: Multi-variable type inference failed */
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
