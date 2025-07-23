package com.airbnb.lottie.animation.content;

import android.graphics.Matrix;
import android.graphics.Path;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MergePathsContent implements PathContent, GreedyContent {
    public final MergePaths mergePaths;
    public final Path firstPath = new Path();
    public final Path remainderPath = new Path();
    public final Path path = new Path();
    public final List pathContents = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.airbnb.lottie.animation.content.MergePathsContent$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode;

        static {
            int[] iArr = new int[MergePaths.MergePathsMode.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode = iArr;
            try {
                iArr[MergePaths.MergePathsMode.MERGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode[MergePaths.MergePathsMode.ADD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode[MergePaths.MergePathsMode.SUBTRACT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode[MergePaths.MergePathsMode.INTERSECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode[MergePaths.MergePathsMode.EXCLUDE_INTERSECTIONS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public MergePathsContent(MergePaths mergePaths) {
        mergePaths.getClass();
        this.mergePaths = mergePaths;
    }

    @Override // com.airbnb.lottie.animation.content.GreedyContent
    public final void absorbContent(ListIterator listIterator) {
        while (listIterator.hasPrevious() && listIterator.previous() != this) {
        }
        while (listIterator.hasPrevious()) {
            Content content = (Content) listIterator.previous();
            if (content instanceof PathContent) {
                ((ArrayList) this.pathContents).add((PathContent) content);
                listIterator.remove();
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        this.path.reset();
        MergePaths mergePaths = this.mergePaths;
        if (mergePaths.hidden) {
            return this.path;
        }
        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode[mergePaths.mode.ordinal()];
        if (i == 1) {
            for (int i2 = 0; i2 < ((ArrayList) this.pathContents).size(); i2++) {
                this.path.addPath(((PathContent) ((ArrayList) this.pathContents).get(i2)).getPath());
            }
        } else if (i == 2) {
            opFirstPathWithRest(Path.Op.UNION);
        } else if (i == 3) {
            opFirstPathWithRest(Path.Op.REVERSE_DIFFERENCE);
        } else if (i == 4) {
            opFirstPathWithRest(Path.Op.INTERSECT);
        } else if (i == 5) {
            opFirstPathWithRest(Path.Op.XOR);
        }
        return this.path;
    }

    public final void opFirstPathWithRest(Path.Op op) {
        Matrix matrix;
        Matrix matrix2;
        this.remainderPath.reset();
        this.firstPath.reset();
        for (int size = ((ArrayList) this.pathContents).size() - 1; size >= 1; size--) {
            PathContent pathContent = (PathContent) ((ArrayList) this.pathContents).get(size);
            if (pathContent instanceof ContentGroup) {
                ContentGroup contentGroup = (ContentGroup) pathContent;
                List pathList = contentGroup.getPathList();
                for (int size2 = pathList.size() - 1; size2 >= 0; size2--) {
                    Path path = ((PathContent) pathList.get(size2)).getPath();
                    TransformKeyframeAnimation transformKeyframeAnimation = contentGroup.transformAnimation;
                    if (transformKeyframeAnimation != null) {
                        matrix2 = transformKeyframeAnimation.getMatrix();
                    } else {
                        contentGroup.matrix.reset();
                        matrix2 = contentGroup.matrix;
                    }
                    path.transform(matrix2);
                    this.remainderPath.addPath(path);
                }
            } else {
                this.remainderPath.addPath(pathContent.getPath());
            }
        }
        PathContent pathContent2 = (PathContent) ((ArrayList) this.pathContents).get(0);
        if (pathContent2 instanceof ContentGroup) {
            ContentGroup contentGroup2 = (ContentGroup) pathContent2;
            List pathList2 = contentGroup2.getPathList();
            for (int i = 0; i < pathList2.size(); i++) {
                Path path2 = ((PathContent) pathList2.get(i)).getPath();
                TransformKeyframeAnimation transformKeyframeAnimation2 = contentGroup2.transformAnimation;
                if (transformKeyframeAnimation2 != null) {
                    matrix = transformKeyframeAnimation2.getMatrix();
                } else {
                    contentGroup2.matrix.reset();
                    matrix = contentGroup2.matrix;
                }
                path2.transform(matrix);
                this.firstPath.addPath(path2);
            }
        } else {
            this.firstPath.set(pathContent2.getPath());
        }
        this.path.op(this.firstPath, this.remainderPath, op);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        for (int i = 0; i < ((ArrayList) this.pathContents).size(); i++) {
            ((PathContent) ((ArrayList) this.pathContents).get(i)).setContents(list, list2);
        }
    }
}
