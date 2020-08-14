package com.cn.lgf.module.main;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import com.cn.lgf.common.base.IViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.module.main
 * @ClassName: MainActivityViewModel
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/3 1:58 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/3 1:58 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MainActivityViewModel extends IViewModel {
    public final ObservableField<String> name = new ObservableField<>();

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public void onCreate(@NonNull @NotNull LifecycleOwner owner) {
        name.set("11111");
    }
}
