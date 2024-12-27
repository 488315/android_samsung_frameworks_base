package com.android.systemui.assist.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.util.Pair;
import com.android.systemui.assist.ui.CornerPathRenderer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PerimeterPathGuide {
    public final int mBottomCornerRadiusPx;
    public final CornerPathRenderer mCornerPathRenderer;
    public final int mDeviceHeightPx;
    public final int mDeviceWidthPx;
    public final int mEdgeInset;
    public final int mTopCornerRadiusPx;
    public final PathMeasure mScratchPathMeasure = new PathMeasure(new Path(), false);
    public int mRotation = 0;
    public final RegionAttributes[] mRegions = new RegionAttributes[8];

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum Region {
        BOTTOM,
        BOTTOM_RIGHT,
        RIGHT,
        TOP_RIGHT,
        TOP,
        TOP_LEFT,
        LEFT,
        BOTTOM_LEFT
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class RegionAttributes {
        public float absoluteLength;
        public float endCoordinate;
        public float normalizedLength;
        public Path path;

        public /* synthetic */ RegionAttributes(PerimeterPathGuide perimeterPathGuide, int i) {
            this(perimeterPathGuide);
        }

        private RegionAttributes(PerimeterPathGuide perimeterPathGuide) {
        }
    }

    public PerimeterPathGuide(Context context, CornerPathRenderer cornerPathRenderer, int i, int i2, int i3) {
        int i4 = 0;
        this.mCornerPathRenderer = cornerPathRenderer;
        this.mDeviceWidthPx = i2;
        this.mDeviceHeightPx = i3;
        this.mTopCornerRadiusPx = DisplayUtils.getInvocationCornerRadius(context, false);
        this.mBottomCornerRadiusPx = DisplayUtils.getInvocationCornerRadius(context, true);
        this.mEdgeInset = i;
        int i5 = 0;
        while (true) {
            RegionAttributes[] regionAttributesArr = this.mRegions;
            if (i5 >= regionAttributesArr.length) {
                computeRegions();
                return;
            } else {
                regionAttributesArr[i5] = new RegionAttributes(this, i4);
                i5++;
            }
        }
    }

    public final void computeRegions() {
        int i = this.mRotation;
        int i2 = i != 1 ? i != 2 ? i != 3 ? 0 : -270 : -180 : -90;
        Matrix matrix = new Matrix();
        int i3 = this.mDeviceWidthPx;
        int i4 = this.mDeviceHeightPx;
        matrix.postRotate(i2, i3 / 2, i4 / 2);
        int i5 = this.mRotation;
        if (i5 == 1 || i5 == 3) {
            matrix.postTranslate((i4 - i3) / 2, (i3 - i4) / 2);
        } else {
            i4 = i3;
            i3 = i4;
        }
        CornerPathRenderer.Corner rotatedCorner = getRotatedCorner(CornerPathRenderer.Corner.BOTTOM_LEFT);
        CornerPathRenderer.Corner rotatedCorner2 = getRotatedCorner(CornerPathRenderer.Corner.BOTTOM_RIGHT);
        CornerPathRenderer.Corner rotatedCorner3 = getRotatedCorner(CornerPathRenderer.Corner.TOP_LEFT);
        CornerPathRenderer.Corner rotatedCorner4 = getRotatedCorner(CornerPathRenderer.Corner.TOP_RIGHT);
        Region region = Region.BOTTOM_LEFT;
        int ordinal = region.ordinal();
        RegionAttributes[] regionAttributesArr = this.mRegions;
        RegionAttributes regionAttributes = regionAttributesArr[ordinal];
        int i6 = this.mEdgeInset;
        CornerPathRenderer cornerPathRenderer = this.mCornerPathRenderer;
        regionAttributes.path = cornerPathRenderer.getInsetPath(rotatedCorner, i6);
        Region region2 = Region.BOTTOM_RIGHT;
        regionAttributesArr[region2.ordinal()].path = cornerPathRenderer.getInsetPath(rotatedCorner2, i6);
        Region region3 = Region.TOP_RIGHT;
        regionAttributesArr[region3.ordinal()].path = cornerPathRenderer.getInsetPath(rotatedCorner4, i6);
        Region region4 = Region.TOP_LEFT;
        regionAttributesArr[region4.ordinal()].path = cornerPathRenderer.getInsetPath(rotatedCorner3, i6);
        regionAttributesArr[region.ordinal()].path.transform(matrix);
        regionAttributesArr[region2.ordinal()].path.transform(matrix);
        regionAttributesArr[region3.ordinal()].path.transform(matrix);
        regionAttributesArr[region4.ordinal()].path.transform(matrix);
        Path path = new Path();
        path.moveTo(getPhysicalCornerRadius(rotatedCorner), i3 - i6);
        path.lineTo(i4 - getPhysicalCornerRadius(rotatedCorner2), i3 - i6);
        regionAttributesArr[Region.BOTTOM.ordinal()].path = path;
        Path path2 = new Path();
        path2.moveTo(i4 - getPhysicalCornerRadius(rotatedCorner4), i6);
        path2.lineTo(getPhysicalCornerRadius(rotatedCorner3), i6);
        regionAttributesArr[Region.TOP.ordinal()].path = path2;
        Path path3 = new Path();
        path3.moveTo(i4 - i6, i3 - getPhysicalCornerRadius(rotatedCorner2));
        path3.lineTo(i4 - i6, getPhysicalCornerRadius(rotatedCorner4));
        regionAttributesArr[Region.RIGHT.ordinal()].path = path3;
        Path path4 = new Path();
        path4.moveTo(i6, getPhysicalCornerRadius(rotatedCorner3));
        path4.lineTo(i6, i3 - getPhysicalCornerRadius(rotatedCorner));
        regionAttributesArr[Region.LEFT.ordinal()].path = path4;
        PathMeasure pathMeasure = new PathMeasure();
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i7 = 0; i7 < regionAttributesArr.length; i7++) {
            pathMeasure.setPath(regionAttributesArr[i7].path, false);
            regionAttributesArr[i7].absoluteLength = pathMeasure.getLength();
            f2 += regionAttributesArr[i7].absoluteLength;
        }
        for (RegionAttributes regionAttributes2 : regionAttributesArr) {
            float f3 = regionAttributes2.absoluteLength;
            regionAttributes2.normalizedLength = f3 / f2;
            f += f3;
            regionAttributes2.endCoordinate = f / f2;
        }
        regionAttributesArr[regionAttributesArr.length - 1].endCoordinate = 1.0f;
    }

    public final int getPhysicalCornerRadius(CornerPathRenderer.Corner corner) {
        return (corner == CornerPathRenderer.Corner.BOTTOM_LEFT || corner == CornerPathRenderer.Corner.BOTTOM_RIGHT) ? this.mBottomCornerRadiusPx : this.mTopCornerRadiusPx;
    }

    public final CornerPathRenderer.Corner getRotatedCorner(CornerPathRenderer.Corner corner) {
        int ordinal = corner.ordinal();
        int i = this.mRotation;
        if (i == 1) {
            ordinal += 3;
        } else if (i == 2) {
            ordinal += 2;
        } else if (i == 3) {
            ordinal++;
        }
        return CornerPathRenderer.Corner.values()[ordinal % 4];
    }

    public final Pair placePoint(float f) {
        RegionAttributes[] regionAttributesArr;
        Region region;
        if (0.0f > f || f > 1.0f) {
            f = ((f % 1.0f) + 1.0f) % 1.0f;
        }
        float f2 = (f < 0.0f || f > 1.0f) ? ((f % 1.0f) + 1.0f) % 1.0f : f;
        Region[] values = Region.values();
        int length = values.length;
        int i = 0;
        while (true) {
            regionAttributesArr = this.mRegions;
            if (i >= length) {
                Log.e("PerimeterPathGuide", "Fell out of getRegionForPoint");
                region = Region.BOTTOM;
                break;
            }
            region = values[i];
            if (f2 <= regionAttributesArr[region.ordinal()].endCoordinate) {
                break;
            }
            i++;
        }
        return region.equals(Region.BOTTOM) ? Pair.create(region, Float.valueOf(f / regionAttributesArr[region.ordinal()].normalizedLength)) : Pair.create(region, Float.valueOf((f - regionAttributesArr[region.ordinal() - 1].endCoordinate) / regionAttributesArr[region.ordinal()].normalizedLength));
    }

    public final void strokeRegion(Path path, Region region, float f, float f2) {
        if (f == f2) {
            return;
        }
        this.mScratchPathMeasure.setPath(this.mRegions[region.ordinal()].path, false);
        PathMeasure pathMeasure = this.mScratchPathMeasure;
        pathMeasure.getSegment(pathMeasure.getLength() * f, this.mScratchPathMeasure.getLength() * f2, path, true);
    }

    public final void strokeSegmentInternal(Path path, float f, float f2) {
        Pair placePoint = placePoint(f);
        Pair placePoint2 = placePoint(f2);
        if (((Region) placePoint.first).equals(placePoint2.first)) {
            strokeRegion(path, (Region) placePoint.first, ((Float) placePoint.second).floatValue(), ((Float) placePoint2.second).floatValue());
            return;
        }
        strokeRegion(path, (Region) placePoint.first, ((Float) placePoint.second).floatValue(), 1.0f);
        boolean z = false;
        for (Region region : Region.values()) {
            if (region.equals(placePoint.first)) {
                z = true;
            } else if (!z) {
                continue;
            } else {
                if (region.equals(placePoint2.first)) {
                    strokeRegion(path, region, 0.0f, ((Float) placePoint2.second).floatValue());
                    return;
                }
                strokeRegion(path, region, 0.0f, 1.0f);
            }
        }
    }
}
