package com.cn.lgf.common.databinding;

import android.util.SparseArray;
import androidx.lifecycle.ViewModel;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.databinding
 * @ClassName: DataBindingConfig
 * @Description: 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图调用的一致性问题，
 * 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
 * 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
 * @Author: 作者名
 * @CreateDate: 2020/7/31 2:03 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/31 2:03 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DataBindingConfig {
    private int layout;

    private int vmVariableId;

    private ViewModel stateViewModel;

    private SparseArray bindingParams = new SparseArray();

    public DataBindingConfig(int layout, int vmVariableId, ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmVariableId() {
        return vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParam(int variableId, Object object) {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, object);
        }
        return this;
    }
}
