package com.samsung.android.graphics.imagefilter;

import android.graphics.RenderEffect;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.filters.BlurFilter;
import com.samsung.android.graphics.imagefilter.filters.CurveFilter;
import com.samsung.android.graphics.imagefilter.filters.DitherFilter;
import com.samsung.android.graphics.imagefilter.filters.ProSatuationFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public final class ShaderAssembler {
    public static final String NEWLINE = "\n";
    public static final String SHADER_MAIN_CODE_END = "}\n";
    public static final String SHADER_MAIN_CODE_START = "uniform shader viewImage;\nuniform int viewWidth;\nuniform int viewHeight;\nvec4 main(vec2 fragCoord) {\n";
    public static final String SHADER_SAMMPLEDCOLOR_CODE_END = "return sampledColor;\n";
    public static final String SHADER_SAMMPLEDCOLOR_CODE_START = "vec4 sampledColor = viewImage.eval(fragCoord);\n";
    public static final String SHADER_SAMPLE_CLAMP_EXECUTE_CODE = "if (viewWidth > 0 && viewHeight > 0) {\n   fragCoord = clamp(fragCoord, vec2(1, 1), vec2(viewWidth - 1, viewHeight - 1));\n}\n";
    private static final String TAG = "ShaderAssembler";
    public static final Map<Integer, Integer> ALL_PARAMS = Map.ofEntries(Map.entry(0, 1), Map.entry(8, 16), Map.entry(1, 8), Map.entry(7, 4), Map.entry(2, 2), Map.entry(3, 2), Map.entry(4, 2), Map.entry(5, 2), Map.entry(6, 2));
    public static final List<Integer> SHADER_ORDER = List.of(2, 8, 1, 4);
    public final Map<Integer, FilterEffect> all_filters = Map.ofEntries(Map.entry(1, new BlurFilter()), Map.entry(2, new CurveFilter()), Map.entry(8, new ProSatuationFilter()), Map.entry(4, new DitherFilter()));
    private int viewWidth = 0;
    private int viewHeight = 0;
    private final Map<Integer, FilterEffect> filterEffects = new HashMap();
    private final List<AssembledShader> assembledShaders = new ArrayList();

    public void clear() {
        Iterator<Map.Entry<Integer, FilterEffect>> it = this.filterEffects.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().clear();
        }
        this.assembledShaders.clear();
        this.filterEffects.clear();
    }

    public void setSize(int i, int i2) {
        this.viewWidth = i;
        this.viewHeight = i2;
    }

    public void setParam(final int i, final float f) {
        Optional.ofNullable(ALL_PARAMS.get(Integer.valueOf(i))).ifPresent(new Consumer() { // from class: com.samsung.android.graphics.imagefilter.ShaderAssembler$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$setParam$0(i, f, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParam$0(int i, float f, Integer num) {
        try {
            FilterEffect filterEffectRegisterFilter = this.filterEffects.get(num);
            if (filterEffectRegisterFilter == null) {
                filterEffectRegisterFilter = registerFilter(num.intValue());
            }
            if (filterEffectRegisterFilter != null) {
                filterEffectRegisterFilter.setParam(i, f);
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "Error setting parameter for filter type: " + num, e);
        }
    }

    public void updateParams() {
        RuntimeShader runtimeShader;
        for (Map.Entry<Integer, FilterEffect> entry : this.filterEffects.entrySet()) {
            FilterEffect value = entry.getValue();
            Integer key = entry.getKey();
            Iterator<AssembledShader> it = this.assembledShaders.iterator();
            while (true) {
                if (it.hasNext()) {
                    AssembledShader next = it.next();
                    if ((next.getFilterMask() & key.intValue()) != 0 && (runtimeShader = next.getRuntimeShader()) != null) {
                        value.updateShader(runtimeShader);
                        runtimeShader.setIntUniform("viewWidth", this.viewWidth);
                        runtimeShader.setIntUniform("viewHeight", this.viewHeight);
                        break;
                    }
                }
            }
        }
    }

    public String getShaderCode() {
        StringBuilder sb = new StringBuilder("shaderCount : ");
        sb.append(this.assembledShaders.size());
        sb.append(NEWLINE);
        for (AssembledShader assembledShader : this.assembledShaders) {
            sb.append("shaderName : ");
            sb.append(assembledShader.getName());
            sb.append("\n\n");
            if (assembledShader.isAssemble()) {
                sb.append(assembledShader.getShader());
            }
        }
        return sb.toString();
    }

    public RenderEffect getRenderEffect() {
        return createAssembledRenderEffect();
    }

    public void printParams() {
        for (Map.Entry<Integer, FilterEffect> entry : this.filterEffects.entrySet()) {
            FilterEffect value = entry.getValue();
            int iIntValue = entry.getKey().intValue();
            if (iIntValue == 1) {
                Log.d(TAG, "Blur Param : " + value.getParam(0));
            } else if (iIntValue == 2) {
                Log.d(TAG, "Curve Level : " + value.getParam(2) + ", Curve MinX : " + value.getParam(4) + ", Curve MaxX : " + value.getParam(3) + ", Curve MinY : " + value.getParam(6) + ", Curve MaxY : " + value.getParam(5));
            } else if (iIntValue == 4) {
                Log.d(TAG, "DITHER Param : " + value.getParam(7));
            } else if (iIntValue == 8) {
                Log.d(TAG, "PROSATURATION Param : " + value.getParam(1));
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
    
        r3.filterEffects.put(java.lang.Integer.valueOf(r4), r0);
        assembleShaderCodes();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private FilterEffect registerFilter(int i) {
        try {
            FilterEffect filterEffect = this.all_filters.get(Integer.valueOf(i));
            if (filterEffect == null) {
                return null;
            }
            Iterator<Integer> it = SHADER_ORDER.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next().intValue() == i) {
                    break;
                }
            }
            return filterEffect;
        } catch (NullPointerException unused) {
            throw new NullPointerException("Not found filter type in ORDRED_SHADER: " + i);
        }
    }

    private LinkedHashMap<Integer, FilterEffect> getOrderedEffects() {
        LinkedHashMap<Integer, FilterEffect> linkedHashMap = new LinkedHashMap<>();
        for (Integer num : SHADER_ORDER) {
            FilterEffect filterEffect = this.filterEffects.get(num);
            if (filterEffect != null) {
                linkedHashMap.put(num, filterEffect);
            }
        }
        return linkedHashMap;
    }

    private void assembleShaderCodes() {
        ArrayList<List<FilterEffect>> arrayListSplitFilters = splitFilters();
        if (arrayListSplitFilters == null) {
            return;
        }
        this.assembledShaders.clear();
        Iterator<List<FilterEffect>> it = arrayListSplitFilters.iterator();
        while (it.hasNext()) {
            List<FilterEffect> next = it.next();
            int filterType = 0;
            if (next.size() == 1) {
                FilterEffect filterEffect = next.get(0);
                if (filterEffect.useShaderCode()) {
                    StringBuilder sbAssembleShaderCode = assembleShaderCode(next);
                    if (sbAssembleShaderCode != null) {
                        this.assembledShaders.add(new AssembledShader(filterEffect.getFilterType(), sbAssembleShaderCode.toString()));
                    }
                } else {
                    this.assembledShaders.add(new AssembledShader(filterEffect));
                }
            } else {
                Iterator<FilterEffect> it2 = next.iterator();
                while (it2.hasNext()) {
                    filterType += it2.next().getFilterType();
                }
                StringBuilder sbAssembleShaderCode2 = assembleShaderCode(next);
                if (sbAssembleShaderCode2 != null) {
                    this.assembledShaders.add(new AssembledShader(filterType, sbAssembleShaderCode2.toString()));
                }
            }
        }
    }

    private ArrayList<List<FilterEffect>> splitFilters() {
        if (this.filterEffects.isEmpty()) {
            return null;
        }
        LinkedHashMap<Integer, FilterEffect> orderedEffects = getOrderedEffects();
        ArrayList<List<FilterEffect>> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        for (FilterEffect filterEffect : orderedEffects.values()) {
            if (filterEffect.useShaderCode()) {
                arrayList2.add(filterEffect);
            } else {
                if (!arrayList2.isEmpty()) {
                    arrayList.add(arrayList2);
                    arrayList2 = new ArrayList();
                }
                arrayList.add(new ArrayList<FilterEffect>(filterEffect) { // from class: com.samsung.android.graphics.imagefilter.ShaderAssembler.1
                    final /* synthetic */ FilterEffect val$effect;

                    {
                        this.val$effect = filterEffect;
                        add(filterEffect);
                    }
                });
            }
        }
        if (!arrayList2.isEmpty()) {
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    private StringBuilder assembleShaderCode(List<FilterEffect> list) {
        if (list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<FilterEffect> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().getFunctionShaderCode());
        }
        sb.append(NEWLINE);
        Iterator<FilterEffect> it2 = list.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next().getDeclareShaderCode());
        }
        sb.append("uniform shader viewImage;\nuniform int viewWidth;\nuniform int viewHeight;\nvec4 main(vec2 fragCoord) {\nif (viewWidth > 0 && viewHeight > 0) {\n   fragCoord = clamp(fragCoord, vec2(1, 1), vec2(viewWidth - 1, viewHeight - 1));\n}\nvec4 sampledColor = viewImage.eval(fragCoord);\n");
        Iterator<FilterEffect> it3 = list.iterator();
        while (it3.hasNext()) {
            sb.append(it3.next().getMainShaderCode());
        }
        sb.append("return sampledColor;\n}\n\n");
        return sb;
    }

    private RenderEffect createAssembledRenderEffect() {
        RenderEffect renderEffectCreateBlurEffect = null;
        for (AssembledShader assembledShader : this.assembledShaders) {
            int filterMask = assembledShader.getFilterMask();
            if (filterMask != 0) {
                if ((filterMask & 1) != 0) {
                    FilterEffect filterEffect = this.filterEffects.get(1);
                    if (filterEffect != null) {
                        float param = filterEffect.getParam(0);
                        if (renderEffectCreateBlurEffect == null) {
                            renderEffectCreateBlurEffect = RenderEffect.createBlurEffect(param, param, Shader.TileMode.CLAMP);
                        } else {
                            renderEffectCreateBlurEffect = RenderEffect.createBlurEffect(param, param, renderEffectCreateBlurEffect, Shader.TileMode.CLAMP);
                        }
                    }
                } else {
                    RenderEffect renderEffectCreateRuntimeShaderEffect = RenderEffect.createRuntimeShaderEffect(assembledShader.getRuntimeShader(), "viewImage");
                    renderEffectCreateBlurEffect = renderEffectCreateBlurEffect == null ? renderEffectCreateRuntimeShaderEffect : RenderEffect.createChainEffect(renderEffectCreateRuntimeShaderEffect, renderEffectCreateBlurEffect);
                }
            }
        }
        return renderEffectCreateBlurEffect;
    }

    private RenderEffect createChainRenderEffect() {
        RenderEffect renderEffect = null;
        for (FilterEffect filterEffect : getOrderedEffects().values()) {
            if (renderEffect == null) {
                renderEffect = filterEffect.getRenderEffect();
            } else {
                renderEffect = RenderEffect.createChainEffect(filterEffect.getRenderEffect(), renderEffect);
            }
        }
        return renderEffect;
    }

    private void printDebug(ArrayList<List<FilterEffect>> arrayList) {
        Log.d(TAG, "AssembleShaderCodes : " + arrayList.size());
        Iterator<List<FilterEffect>> it = arrayList.iterator();
        while (it.hasNext()) {
            Iterator<FilterEffect> it2 = it.next().iterator();
            while (it2.hasNext()) {
                Log.d(TAG, it2.next().getFilterName());
            }
        }
    }
}
