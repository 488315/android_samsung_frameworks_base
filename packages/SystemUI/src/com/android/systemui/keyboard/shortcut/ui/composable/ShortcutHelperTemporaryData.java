package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AccessibilityKt;
import androidx.compose.material.icons.filled.AppsKt;
import androidx.compose.material.icons.filled.ArrowBackIosNewKt;
import androidx.compose.material.icons.filled.CloseKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.filled.KeyboardCommandKeyKt;
import androidx.compose.material.icons.filled.KeyboardCommandKeyKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.filled.KeyboardKt;
import androidx.compose.material.icons.filled.RadioButtonUncheckedKt;
import androidx.compose.material.icons.filled.TvKt;
import androidx.compose.material.icons.filled.VerticalSplitKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKey;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShortcutHelperTemporaryData {
    public static final ShortcutHelperTemporaryData INSTANCE = new ShortcutHelperTemporaryData();
    public static final List categories;

    static {
        Icons.INSTANCE.getClass();
        ImageVector imageVector = TvKt._tv;
        if (imageVector == null) {
            Dp.Companion companion = Dp.Companion;
            ImageVector.Builder builder = new ImageVector.Builder("Filled.Tv", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
            EmptyList emptyList = VectorKt.EmptyPath;
            Color.Companion.getClass();
            SolidColor solidColor = new SolidColor(Color.Black, null);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            int i = StrokeJoin.Bevel;
            PathBuilder m = CloseKt$$ExternalSyntheticOutline0.m(21.0f, 3.0f, 3.0f, 3.0f);
            m.curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f);
            m.verticalLineToRelative(12.0f);
            m.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
            m.horizontalLineToRelative(5.0f);
            m.verticalLineToRelative(2.0f);
            m.horizontalLineToRelative(8.0f);
            m.verticalLineToRelative(-2.0f);
            m.horizontalLineToRelative(5.0f);
            m.curveToRelative(1.1f, 0.0f, 1.99f, -0.9f, 1.99f, -2.0f);
            m.lineTo(23.0f, 5.0f);
            m.curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f);
            m.close();
            m.moveTo(21.0f, 17.0f);
            m.lineTo(3.0f, 17.0f);
            m.lineTo(3.0f, 5.0f);
            m.horizontalLineToRelative(18.0f);
            m.verticalLineToRelative(12.0f);
            m.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder, m._nodes, 0, solidColor, null, 1.0f, 0, i, 1.0f);
            imageVector = builder.build();
            TvKt._tv = imageVector;
        }
        ShortcutHelperCategory access$shortcutHelperCategory = ShortcutHelperTemporaryDataKt.access$shortcutHelperCategory(R.string.shortcut_helper_category_system, imageVector, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ShortcutHelperCategoryBuilder shortcutHelperCategoryBuilder = (ShortcutHelperCategoryBuilder) obj;
                ShortcutHelperTemporaryDataKt.access$subCategory(shortcutHelperCategoryBuilder, "System controls", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        SubCategoryBuilder subCategoryBuilder = (SubCategoryBuilder) obj2;
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Go to home screen", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutBuilder shortcutBuilder = (ShortcutBuilder) obj3;
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ImageVector imageVector2 = RadioButtonUncheckedKt._radioButtonUnchecked;
                                        if (imageVector2 == null) {
                                            Dp.Companion companion2 = Dp.Companion;
                                            ImageVector.Builder builder2 = new ImageVector.Builder("Filled.RadioButtonUnchecked", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                                            EmptyList emptyList2 = VectorKt.EmptyPath;
                                            Color.Companion.getClass();
                                            SolidColor solidColor2 = new SolidColor(Color.Black, null);
                                            StrokeCap.Companion.getClass();
                                            StrokeJoin.Companion.getClass();
                                            int i2 = StrokeJoin.Bevel;
                                            PathBuilder pathBuilder = new PathBuilder();
                                            pathBuilder.moveTo(12.0f, 2.0f);
                                            pathBuilder.curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f);
                                            pathBuilder.reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f);
                                            pathBuilder.reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f);
                                            pathBuilder.reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f);
                                            pathBuilder.close();
                                            pathBuilder.moveTo(12.0f, 20.0f);
                                            pathBuilder.curveToRelative(-4.42f, 0.0f, -8.0f, -3.58f, -8.0f, -8.0f);
                                            pathBuilder.reflectiveCurveToRelative(3.58f, -8.0f, 8.0f, -8.0f);
                                            pathBuilder.reflectiveCurveToRelative(8.0f, 3.58f, 8.0f, 8.0f);
                                            pathBuilder.reflectiveCurveToRelative(-3.58f, 8.0f, -8.0f, 8.0f);
                                            pathBuilder.close();
                                            ImageVector.Builder.m491addPathoIyEayM$default(builder2, pathBuilder._nodes, 0, solidColor2, null, 1.0f, 0, i2, 1.0f);
                                            imageVector2 = builder2.build();
                                            RadioButtonUncheckedKt._radioButtonUnchecked = imageVector2;
                                        }
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(imageVector2));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.1.2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text(ImsProfile.TIMER_NAME_H));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.1.3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Return"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "View recent apps", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Tab"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "All apps search", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.3
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.1.3.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                });
                ShortcutHelperTemporaryDataKt.access$subCategory(shortcutHelperCategoryBuilder, "System apps", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$1.2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        SubCategoryBuilder subCategoryBuilder = (SubCategoryBuilder) obj2;
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Go back", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutBuilder shortcutBuilder = (ShortcutBuilder) obj3;
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Icon(ArrowBackIosNewKt.getArrowBackIosNew()));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.1.2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Left arrow"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.1.3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("ESC"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.1.4
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Backspace"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "View notifications", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("N"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Take a screenshot", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.3
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutBuilder shortcutBuilder = (ShortcutBuilder) obj3;
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.3.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.3.2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Text("CTRL"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.3.3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Text("S"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Open Settings", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.4
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.1.2.4.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text(ImsProfile.TIMER_NAME_I));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        });
        ImageVector imageVector2 = VerticalSplitKt._verticalSplit;
        if (imageVector2 == null) {
            Dp.Companion companion2 = Dp.Companion;
            ImageVector.Builder builder2 = new ImageVector.Builder("Filled.VerticalSplit", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
            EmptyList emptyList2 = VectorKt.EmptyPath;
            Color.Companion.getClass();
            SolidColor solidColor2 = new SolidColor(Color.Black, null);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            int i2 = StrokeJoin.Bevel;
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(3.0f, 15.0f);
            pathBuilder.horizontalLineToRelative(8.0f);
            pathBuilder.verticalLineToRelative(-2.0f);
            pathBuilder.lineTo(3.0f, 13.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.close();
            pathBuilder.moveTo(3.0f, 19.0f);
            pathBuilder.horizontalLineToRelative(8.0f);
            pathBuilder.verticalLineToRelative(-2.0f);
            pathBuilder.lineTo(3.0f, 17.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.close();
            pathBuilder.moveTo(3.0f, 11.0f);
            pathBuilder.horizontalLineToRelative(8.0f);
            pathBuilder.lineTo(11.0f, 9.0f);
            pathBuilder.lineTo(3.0f, 9.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.close();
            pathBuilder.moveTo(3.0f, 5.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.horizontalLineToRelative(8.0f);
            CloseKt$$ExternalSyntheticOutline0.m(pathBuilder, 11.0f, 5.0f, 3.0f, 5.0f);
            pathBuilder.moveTo(13.0f, 5.0f);
            pathBuilder.horizontalLineToRelative(8.0f);
            pathBuilder.verticalLineToRelative(14.0f);
            pathBuilder.horizontalLineToRelative(-8.0f);
            pathBuilder.lineTo(13.0f, 5.0f);
            pathBuilder.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder2, pathBuilder._nodes, 0, solidColor2, null, 1.0f, 0, i2, 1.0f);
            imageVector2 = builder2.build();
            VerticalSplitKt._verticalSplit = imageVector2;
        }
        ShortcutHelperCategory access$shortcutHelperCategory2 = ShortcutHelperTemporaryDataKt.access$shortcutHelperCategory(R.string.shortcut_helper_category_multitasking, imageVector2, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ShortcutHelperTemporaryDataKt.access$subCategory((ShortcutHelperCategoryBuilder) obj, "Multitasking & windows", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        ShortcutHelperTemporaryDataKt.access$shortcut((SubCategoryBuilder) obj2, "Take a screenshot", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.2.1.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutBuilder shortcutBuilder = (ShortcutBuilder) obj3;
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.2.1.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.2.1.1.2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Text("CTRL"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.2.1.1.3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Text("S"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        });
        ImageVector imageVector3 = KeyboardKt._keyboard;
        if (imageVector3 == null) {
            Dp.Companion companion3 = Dp.Companion;
            ImageVector.Builder builder3 = new ImageVector.Builder("Filled.Keyboard", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
            EmptyList emptyList3 = VectorKt.EmptyPath;
            Color.Companion.getClass();
            SolidColor solidColor3 = new SolidColor(Color.Black, null);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            int i3 = StrokeJoin.Bevel;
            PathBuilder m2 = CloseKt$$ExternalSyntheticOutline0.m(20.0f, 5.0f, 4.0f, 5.0f);
            m2.curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f);
            m2.lineTo(2.0f, 17.0f);
            m2.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
            m2.horizontalLineToRelative(16.0f);
            m2.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
            m2.lineTo(22.0f, 7.0f);
            m2.curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f);
            m2.close();
            m2.moveTo(11.0f, 8.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.horizontalLineToRelative(-2.0f);
            m2.lineTo(11.0f, 8.0f);
            m2.close();
            m2.moveTo(11.0f, 11.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.horizontalLineToRelative(-2.0f);
            m2.verticalLineToRelative(-2.0f);
            m2.close();
            m2.moveTo(8.0f, 8.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.lineTo(8.0f, 10.0f);
            KeyboardCommandKeyKt$$ExternalSyntheticOutline0.m(m2, 8.0f, 8.0f, 8.0f, 11.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.lineTo(8.0f, 13.0f);
            m2.verticalLineToRelative(-2.0f);
            m2.close();
            m2.moveTo(7.0f, 13.0f);
            m2.lineTo(5.0f, 13.0f);
            m2.verticalLineToRelative(-2.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            m2.moveTo(7.0f, 10.0f);
            m2.lineTo(5.0f, 10.0f);
            m2.lineTo(5.0f, 8.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            m2.moveTo(16.0f, 17.0f);
            m2.lineTo(8.0f, 17.0f);
            m2.verticalLineToRelative(-2.0f);
            m2.horizontalLineToRelative(8.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            m2.moveTo(16.0f, 13.0f);
            m2.horizontalLineToRelative(-2.0f);
            m2.verticalLineToRelative(-2.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            m2.moveTo(16.0f, 10.0f);
            m2.horizontalLineToRelative(-2.0f);
            m2.lineTo(14.0f, 8.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            m2.moveTo(19.0f, 13.0f);
            m2.horizontalLineToRelative(-2.0f);
            m2.verticalLineToRelative(-2.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            m2.moveTo(19.0f, 10.0f);
            m2.horizontalLineToRelative(-2.0f);
            m2.lineTo(17.0f, 8.0f);
            m2.horizontalLineToRelative(2.0f);
            m2.verticalLineToRelative(2.0f);
            m2.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder3, m2._nodes, 0, solidColor3, null, 1.0f, 0, i3, 1.0f);
            imageVector3 = builder3.build();
            KeyboardKt._keyboard = imageVector3;
        }
        ShortcutHelperCategory access$shortcutHelperCategory3 = ShortcutHelperTemporaryDataKt.access$shortcutHelperCategory(R.string.shortcut_helper_category_input, imageVector3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ShortcutHelperTemporaryDataKt.access$subCategory((ShortcutHelperCategoryBuilder) obj, "Input", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$3.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        SubCategoryBuilder subCategoryBuilder = (SubCategoryBuilder) obj2;
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Open Settings", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.3.1.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.3.1.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text(ImsProfile.TIMER_NAME_I));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "View notifications", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.3.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.3.1.2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("N"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        });
        ImageVector imageVector4 = AppsKt._apps;
        if (imageVector4 == null) {
            Dp.Companion companion4 = Dp.Companion;
            ImageVector.Builder builder4 = new ImageVector.Builder("Filled.Apps", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
            EmptyList emptyList4 = VectorKt.EmptyPath;
            Color.Companion.getClass();
            SolidColor solidColor4 = new SolidColor(Color.Black, null);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            int i4 = StrokeJoin.Bevel;
            PathBuilder pathBuilder2 = new PathBuilder();
            pathBuilder2.moveTo(4.0f, 8.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.lineTo(8.0f, 4.0f);
            pathBuilder2.lineTo(4.0f, 4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(10.0f, 20.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.verticalLineToRelative(-4.0f);
            pathBuilder2.horizontalLineToRelative(-4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(4.0f, 20.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.verticalLineToRelative(-4.0f);
            pathBuilder2.lineTo(4.0f, 16.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(4.0f, 14.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.verticalLineToRelative(-4.0f);
            pathBuilder2.lineTo(4.0f, 10.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(10.0f, 14.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.verticalLineToRelative(-4.0f);
            pathBuilder2.horizontalLineToRelative(-4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(16.0f, 4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.lineTo(20.0f, 4.0f);
            pathBuilder2.horizontalLineToRelative(-4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(10.0f, 8.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.lineTo(14.0f, 4.0f);
            pathBuilder2.horizontalLineToRelative(-4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(16.0f, 14.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.verticalLineToRelative(-4.0f);
            pathBuilder2.horizontalLineToRelative(-4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            pathBuilder2.moveTo(16.0f, 20.0f);
            pathBuilder2.horizontalLineToRelative(4.0f);
            pathBuilder2.verticalLineToRelative(-4.0f);
            pathBuilder2.horizontalLineToRelative(-4.0f);
            pathBuilder2.verticalLineToRelative(4.0f);
            pathBuilder2.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder4, pathBuilder2._nodes, 0, solidColor4, null, 1.0f, 0, i4, 1.0f);
            imageVector4 = builder4.build();
            AppsKt._apps = imageVector4;
        }
        ShortcutHelperCategory access$shortcutHelperCategory4 = ShortcutHelperTemporaryDataKt.access$shortcutHelperCategory(R.string.shortcut_helper_category_app_shortcuts, imageVector4, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ShortcutHelperTemporaryDataKt.access$subCategory((ShortcutHelperCategoryBuilder) obj, "App shortcuts", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$4.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        SubCategoryBuilder subCategoryBuilder = (SubCategoryBuilder) obj2;
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Open Settings", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text(ImsProfile.TIMER_NAME_I));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "Go back", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutBuilder shortcutBuilder = (ShortcutBuilder) obj3;
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Icon(ArrowBackIosNewKt.getArrowBackIosNew()));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.2.2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Left arrow"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.2.3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("ESC"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                ShortcutHelperTemporaryDataKt.access$command(shortcutBuilder, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.4.1.2.4
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Backspace"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        });
        ImageVector imageVector5 = AccessibilityKt._accessibility;
        if (imageVector5 == null) {
            Dp.Companion companion5 = Dp.Companion;
            ImageVector.Builder builder5 = new ImageVector.Builder("Filled.Accessibility", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
            EmptyList emptyList5 = VectorKt.EmptyPath;
            Color.Companion.getClass();
            SolidColor solidColor5 = new SolidColor(Color.Black, null);
            StrokeCap.Companion.getClass();
            StrokeJoin.Companion.getClass();
            int i5 = StrokeJoin.Bevel;
            PathBuilder pathBuilder3 = new PathBuilder();
            pathBuilder3.moveTo(12.0f, 2.0f);
            pathBuilder3.curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f);
            pathBuilder3.reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f);
            pathBuilder3.reflectiveCurveToRelative(-2.0f, -0.9f, -2.0f, -2.0f);
            pathBuilder3.reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f);
            pathBuilder3.close();
            pathBuilder3.moveTo(21.0f, 9.0f);
            pathBuilder3.horizontalLineToRelative(-6.0f);
            pathBuilder3.verticalLineToRelative(13.0f);
            pathBuilder3.horizontalLineToRelative(-2.0f);
            pathBuilder3.verticalLineToRelative(-6.0f);
            pathBuilder3.horizontalLineToRelative(-2.0f);
            pathBuilder3.verticalLineToRelative(6.0f);
            pathBuilder3.lineTo(9.0f, 22.0f);
            pathBuilder3.lineTo(9.0f, 9.0f);
            pathBuilder3.lineTo(3.0f, 9.0f);
            pathBuilder3.lineTo(3.0f, 7.0f);
            pathBuilder3.horizontalLineToRelative(18.0f);
            pathBuilder3.verticalLineToRelative(2.0f);
            pathBuilder3.close();
            ImageVector.Builder.m491addPathoIyEayM$default(builder5, pathBuilder3._nodes, 0, solidColor5, null, 1.0f, 0, i5, 1.0f);
            imageVector5 = builder5.build();
            AccessibilityKt._accessibility = imageVector5;
        }
        categories = CollectionsKt__CollectionsKt.listOf(access$shortcutHelperCategory, access$shortcutHelperCategory2, access$shortcutHelperCategory3, access$shortcutHelperCategory4, ShortcutHelperTemporaryDataKt.access$shortcutHelperCategory(R.string.shortcut_helper_category_a11y, imageVector5, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ShortcutHelperTemporaryDataKt.access$subCategory((ShortcutHelperCategoryBuilder) obj, "Accessibility shortcuts", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData$categories$5.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        SubCategoryBuilder subCategoryBuilder = (SubCategoryBuilder) obj2;
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "View recent apps", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.5.1.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.5.1.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        ShortcutCommandBuilder shortcutCommandBuilder = (ShortcutCommandBuilder) obj4;
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        ((ArrayList) shortcutCommandBuilder.keys).add(new ShortcutKey.Text("Tab"));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        ShortcutHelperTemporaryDataKt.access$shortcut(subCategoryBuilder, "All apps search", new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.5.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                ShortcutHelperTemporaryDataKt.access$command((ShortcutBuilder) obj3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTemporaryData.categories.5.1.2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        Icons.INSTANCE.getClass();
                                        ((ArrayList) ((ShortcutCommandBuilder) obj4).keys).add(new ShortcutKey.Icon(KeyboardCommandKeyKt.getKeyboardCommandKey()));
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        }));
    }

    private ShortcutHelperTemporaryData() {
    }
}
