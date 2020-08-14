package com.cn.lgf.common.mvp;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.mvp
 * @ClassName: MvpPresenter
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/3 11:13 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/3 11:13 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();
}
